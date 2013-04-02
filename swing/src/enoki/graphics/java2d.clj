;; A Java2D implementation of the graphics engine.
;;
;; This may eventually be replaced with something like
;; [Penumbra](https://github.com/ztellman/penumbra).

(ns enoki.graphics.java2d
  (:require [seesaw.core :as seesaw]
            [enoki.event :as event]
            [enoki.logging-macros :as log])
  (:use [enoki.error-macros :only [signal-error]]
        [enoki.graphics])
  (:import [java.awt Canvas Color Dimension Image]))

(defrecord Graphics2DContext [display g]

  Context

  (clear! [this]
    (.clearRect g 0 0 (display-width display) (display-height display))
    this)

  (draw-text! [this s x y]
    (.drawString g (str s) x y)
    this)

  (draw-image! [this img x y]
    (.drawImage g img x y nil)
    this))

(defrecord CanvasDisplay [frame canvas]

  Display

  (init-display! [_]
    (seesaw/invoke-now
     (seesaw/pack! frame)
     (seesaw/show! frame))
    (.createBufferStrategy canvas 2)
    (log/debug (format "width=%d, height=%d" (.getWidth canvas) (.getHeight canvas))))

  (display-width [_]
    (.getWidth canvas))

  (display-height [_]
    (.getHeight canvas))

  (render [this f]
    (let [bs (.getBufferStrategy canvas)
          g (.getDrawGraphics bs)]
      (try
        (f (->Graphics2DContext this g))
        (catch Exception e
          (signal-error "Rendering error" e))
        (finally
         (.show bs)
         (.dispose g))))))

;; It seems bad and wrong to use an AWT Canvas within a Swing JFrame, but I
;; couldn't find a more reliable way to get double-buffering working.
(defn- create-canvas []
  (doto (Canvas.)
    (.setSize 640 480)
    (.setIgnoreRepaint true)))

(defn create-display [frame]
  (let [canvas (create-canvas)]
    (.add frame canvas)
    (->CanvasDisplay frame canvas)))

;; A Java2D implementation of the graphics engine
;; This will probably be replaced with something like
;; Penumbra (https://github.com/ztellman/penumbra)

(ns enoki.graphics.java2d
  (:require [seesaw.core :as seesaw]
            [enoki.event :as event]
            [enoki.util.logging]
            [enoki.util.logging-macros :as log])
  (:use [enoki.graphics])
  (:import [java.awt Canvas Color Dimension]))

(defrecord Graphics2DContext [display g]
  Context
  (clear! [this]
    (.clearRect g 0 0 (display-width display) (display-height display))
    this)
  (draw-text! [this s x y]
    (.drawString g (str s) x y)
    this))

(defrecord CanvasDisplay [frame canvas]
  Display
  (init-display! [_]
    (seesaw/invoke-now
     (seesaw/pack! frame)
     (seesaw/show! frame))
    (.createBufferStrategy canvas 2)
    (printf "width=%d, height=%d%n" (.getWidth canvas) (.getHeight canvas)))
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
          (log/error (.getMessage e)))
        (finally
         (.show bs)
         (.dispose g))))))

;; It seems bad and wrong to use an AWT Canvas within a Swing JFrame, but I
;; couldn't find a more reliable way to get double-buffering working.
(defn- create-canvas []
  (doto (Canvas.)
    (.setSize 640 480)
    (.setIgnoreRepaint true)))

(defn- create-frame [canvas]
  (doto (seesaw/frame :resizable? false)
    (.add canvas)
    (.setIgnoreRepaint true)))

(defn create-display []
  (let [canvas (create-canvas)
        frame (create-frame canvas)]
    (->CanvasDisplay frame canvas)))

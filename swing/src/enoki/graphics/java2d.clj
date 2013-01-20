;; A Java2D implementation of the graphics engine
;; This will probably be replaced with something like
;; Penumbra (https://github.com/ztellman/penumbra)

(ns enoki.graphics.java2d
  (:require [seesaw.core :as seesaw])
  (:use [enoki.graphics]))

(defn create-canvas []
  (doto (seesaw/canvas :id :canvas
                       :background "#000000")
    (.setIgnoreRepaint true)))

(defn create-frame [screen]
  (doto (seesaw/frame :width 640
                      :height 480
                      :content screen)
    (.setIgnoreRepaint true)))

(defrecord Graphics2DContext [display ctx]
  Context
  (clear! [this]
    (.clearRect ctx 0 0 (display-width display) (display-height display))
    this)
  (draw-text! [this s x y]
    (.drawString ctx s x y)
    this))

(defrecord JComponentDisplay [panel]
  Display
  (display-width [_]
    (.getWidth panel))
  (display-height [_]
    (.getHeight panel))
  (render [this f]
    (->> (.getGraphics panel)
         (->Graphics2DContext this)
         (f))))

(defn create-display []
  (let [canvas (create-canvas)]
    (seesaw/invoke-now (seesaw/show! (create-frame canvas)))
    (->JComponentDisplay canvas)))

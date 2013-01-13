;; A Java2D implementation of the graphics engine
;; This will probably be replaced with something like
;; Penumbra (https://github.com/ztellman/penumbra)

(ns enoki.graphics.java2d
  (:use [enoki.graphics]))

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
  (context [this]
    (->Graphics2DContext this (.getGraphics panel))))


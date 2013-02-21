;; An HTML5 `<canvas>`-based graphics implementation.

(ns enoki.graphics.canvas
  (:use [enoki.graphics :only [Context Display display-width display-height]]))

(defrecord CanvasContext [display ctx]

  Context

  (clear! [this]
    (.clearRect ctx 0 0 (display-width display) (display-height display))
    this)

  (draw-text! [this s x y]
    (.fillText ctx s x y)
    this)

  (draw-image! [this img x y]
    (.drawImage ctx img x y)
    this))

(defrecord CanvasDisplay [canvas]

  Display

  (init-display! [_]
    ;; Nothing to do here
    )

  (display-width [_]
    (.-width canvas))

  (display-height [_]
    (.-height canvas))

  (render [this f]
    (->> (.getContext canvas "2d")
         (->CanvasContext this)
         (f))))

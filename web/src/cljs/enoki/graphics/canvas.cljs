;; An HTML5 `<canvas>`-based graphics implementation.

(ns enoki.graphics.canvas
  (:require [enoki.error])
  (:use [enoki.graphics :only [Context Display display-width display-height]])
  (:use-macros [enoki.error-macros :only [signal-error]]))

(defrecord CanvasContext [display ctx]

  Context

  (clear! [this]
    (.clearRect ctx 0 0 (display-width display) (display-height display))
    this)

  (clear! [this colour]
    (with-properties ctx {:background colour}
      (clear! this)))

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
    (try
      (->> (.getContext canvas "2d")
           (->CanvasContext this)
           (f))
      (catch js/Error e
        (signal-error "Rendering error" e)))))

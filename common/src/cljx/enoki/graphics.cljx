;; Graphics operations.

(ns enoki.graphics)

(defprotocol Context

  (clear! [this]
    "Fill the display with the current background colour and return `this`.")

  (draw-text! [this s x y]
    "Draw string `s' at location `(x, y)` and return `this`."))

(defprotocol Display

  (init-display! [this]
    "Do any required initialisation, e.g. make frame visible.")

  (display-width [this]
    "Return the display width in pixels.")

  (display-height [this]
    "Return the display height in pixels.")

  (render [this ctx]
    "Draws to the display using a function `(fn [g])`, where `g` is a `Context`."))

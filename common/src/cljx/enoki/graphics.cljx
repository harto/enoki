;; Graphics operations

(ns enoki.graphics)

(defprotocol Context
  (clear! [this]
    "Fill the display with the current background-colour. Returns `this'.")
  (draw-text! [this s x y]
    "Draw string `s' at location [x, y]"))

(defprotocol Display
  (display-width [this]
    "Return the display width in pixels")
  (display-height [this]
    "Return the display height in pixels")
  (render [this ctx]
    "Draws to the display using a function (fn [g])"))

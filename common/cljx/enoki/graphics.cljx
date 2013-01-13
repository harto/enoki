;; Graphics operations

(ns enoki.graphics)

(def ^:dynamic *display*
  "Platform-specific implementation"
  nil)

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
  (context [this]
    "Initialise and return a Context"))

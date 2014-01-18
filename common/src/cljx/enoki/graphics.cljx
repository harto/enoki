;; # Graphics operations
;;
;; Enoki defines two types for dealing with graphics: `Display` and `Context`.

(ns enoki.graphics)

;; ## Context
;;
;; A `Context` is a mutable set of properties defining the behaviour of various
;; drawing operations. A single property is set using `set-property!`; a group
;; of properties is set using `set-properties!`.

(defprotocol Context

  (save! [this]
    "Push current context state and return `this`.")

  (restore! [this]
    "Pop previous context state and return `this`.")

  (set-property! [this k v]
    "Set a property like `:background` and return `this`.")

  (clear! [this]
    "Fill the display with the current background colour and return `this`.")

  (draw-text! [this s x y]
    "Draw string `s` at location `(x, y)` and return `this`.")

  (draw-image! [this image x y]
    "Draw image `img` at location `(x, y)` and return `this`."))

;;
;; A `Display` is an abstract representation of the graphics display, providing
;;

(defprotocol Display

  (init-display! [this]
    "Do any required initialisation, e.g. make frame visible.")

  (display-width [this]
    "Return the display width in pixels.")

  (display-height [this]
    "Return the display height in pixels.")

  (render [this ctx]
    "Draws to the display using a function `(fn [g])`, where `g` is a `Context`."))

(defn set-properties!
  "Set a map of properties on `ctx`."
  [ctx kvs]
  (doseq [[k v] kvs]
    (set-property! ctx k v)))

(defn with-properties*
  "Execute body with temporarily altered properties on `ctx`."
  [ctx props body]
  (save! ctx)
  (set-properties! ctx props)
  (let [result (body)]
    (restore! ctx)
    result))

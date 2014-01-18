(ns enoki.graphics-macros)

(defmacro with-properties
  "Sets properties on `ctx`, executes body and restores previous state of `ctx`."
  [ctx props & body]
  `(enoki.graphics/with-properties* ~ctx ~props (fn [] ~@body)))

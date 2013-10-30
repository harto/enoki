;; Various miscellaneous utilities

(ns enoki.util)

(defn queue
  ([]
     #+clj clojure.lang.PersistentQueue/EMPTY
     #+cljs cljs.core.PersistentQueue/EMPTY)
  ([& args]
     (into (queue) args)))

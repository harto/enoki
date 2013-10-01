;; Various miscellaneous utilities

(ns enoki.util)

#+clj
(defn queue [& args]
  (into clojure.lang.PersistentQueue/EMPTY args))

#+cljs
(defn queue [& args]
  (into cljs.core.PersistentQueue/EMPTY args))

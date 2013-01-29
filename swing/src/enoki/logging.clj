;; JVM-specific logging implementation.

(ns enoki.logging)

(defn log* [level msg-fn]
  ;; TODO: delegate to clojure.tools.logging?
  (println (msg-fn)))

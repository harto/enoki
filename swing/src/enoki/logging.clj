;; JVM-specific logging implementation.

(ns enoki.logging)

(defn log* [logger-name level msg-fn]
  ;; TODO: delegate to clojure.tools.logging
  (printf "[%s] %s" logger-name (msg-fn)))

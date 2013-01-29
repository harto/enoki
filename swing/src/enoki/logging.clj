;; A JVM-specific logging implementation that defers to
;; [clojure.tools.logging][1]. To get logging working, include one of the
;; logging libraries supported by clojure.tools.logging in your classpath and
;; provide a suitable configuration.
;;
;; One easy way to achieve this is to add a [log4j][2] dependency to your
;; `project.clj` and provide a configuration in `resources/log4j.properties`.
;;
;; [1]: https://github.com/clojure/tools.logging
;; [2]: http://logging.apache.org/log4j/1.2/manual.html

(ns enoki.logging
  (:require [clojure.tools.logging :as impl]))

(defn log* [logger-name level msg-fn]
 (impl/log logger-name level nil (msg-fn)))

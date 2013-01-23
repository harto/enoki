;; Engine entry point for game implementations.

(ns enoki.main
  (:require [enoki.engine :as engine]))

(defn start [env]
  (engine/start env))

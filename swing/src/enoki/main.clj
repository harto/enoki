;; Engine entry point for game implementations.

(ns enoki.main
  (:require [enoki.engine :as engine]))

(defn start
  "Engine entry point"
  [env]
  (engine/start env))

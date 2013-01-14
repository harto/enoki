;; Engine entry point for game implementations.

(ns enoki.main
  (:require [enoki.engine :as engine]
            [enoki.engine-impl :as impl]))

(defn start
  "Engine entry point"
  [env initial-state]
  (engine/start (assoc env :loop-fn impl/loop-forever) initial-state))

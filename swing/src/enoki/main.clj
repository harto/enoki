;; Engine entry point for game implementations.

(ns enoki.main
  (:require [enoki.engine :as engine]
            [enoki.engine-impl :as impl]))

(defn start
  "Engine entry point"
  [env]
  (engine/start (assoc env :loop-fn impl/loop-forever)))

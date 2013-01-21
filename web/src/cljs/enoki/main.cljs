;; Engine entry point for game implementations.

(ns enoki.main
  (:require [clojure.browser.repl :as repl]
            [enoki.engine :as engine]
            [enoki.util.logging :as logging]))

(defn start
  "Engine entry point"
  [env]
  (repl/connect "http://localhost:9000/repl")
  (logging/init!)
  (logging/set-level! :info)
  (engine/start env))

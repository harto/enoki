(ns enoki.web
  (:require [clojure.browser.repl :as repl]
            [enoki.graphics.canvas :as gfx]
            [enoki.util.logging :as logging]
            ))

(defn init! []
  (repl/connect "http://localhost:9000/repl")
  (logging/init!)
  (logging/set-level! :info))

(defn env
  "Initialise and return a web-based environment."
  [canvas-element]
  {:display (gfx/->CanvasDisplay canvas-element)})

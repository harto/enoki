(ns game.web.main
  (:require [clojure.browser.repl :as repl]
            [enoki.engine :as engine]
            [enoki.graphics :as gfx]
            [enoki.graphics.canvas :as gfx-impl]
            [enoki.util.dom :as dom]
            [enoki.util.logging :as logging]))

(def canvas
  (delay (dom/get-element "screen")))

(defn ^:export init []
  (repl/connect "http://localhost:9000/repl")
  (logging/init!)
  (logging/set-level! :info)
  (binding [gfx/*display* (gfx-impl/->CanvasDisplay @canvas)]
    (engine/start)))

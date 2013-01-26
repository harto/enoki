(ns game.web.main
  (:require [game.main :as game]
            [enoki.util.dom :as dom]
            [enoki.web :as web]))

(defn ^:export init []
  (web/init!)
  (game/start (web/env (dom/get-element "screen"))))

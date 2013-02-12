(ns game.web.main
  (:require [clojure.browser.dom :as dom]
            [game.main :as game]
            [enoki.web :as web]))

(defn ^:export init []
  (web/init!)
  (game/start (web/env (dom/get-element "screen"))))

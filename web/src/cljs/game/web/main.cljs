(ns game.web.main
  (:require [game.main :as game]
            [enoki.graphics.canvas :as gfx]
            [enoki.util.dom :as dom]))

(defn ^:export init []
  (game/start {:display (gfx/->CanvasDisplay (dom/get-element "screen"))
               :state {}}))

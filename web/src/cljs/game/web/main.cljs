(ns game.web.main
  (:require [enoki.main :as enoki]
            [enoki.graphics.canvas :as gfx]
            [enoki.util.dom :as dom]))

(defn ^:export init []
  (enoki/start {:display (gfx/->CanvasDisplay (dom/get-element "screen"))
                :state {}}))

(ns game.swing.main
  (:require [game.main :as game]
            [enoki.graphics.java2d :as gfx]))

(defn init []
  (game/start {:display (gfx/create-display)
               :state {}}))

(defn -main [& args]
  (init))

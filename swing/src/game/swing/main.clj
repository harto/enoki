(ns game.swing.main
  (:require [enoki.main :as enoki]
            [enoki.graphics.java2d :as gfx]))


(defn init []
  (enoki/start {:display (gfx/create-display)
                :state {}}))

(defn -main [& args]
  (init))

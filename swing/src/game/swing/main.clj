(ns game.swing.main
  (:require [enoki.swing :as swing]
            [game.main :as game]))

(defn init []
  (game/start (swing/env)))

(defn -main [& args]
  (init))

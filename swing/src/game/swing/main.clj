(ns game.swing.main
  (:require [seesaw.core :as seesaw]
            [enoki.engine :as engine]
            [enoki.graphics :as gfx]
            [enoki.graphics.java2d :as gfx-impl]))

(defn create-screen []
  (doto (seesaw/canvas :id :screen
                   :background "#000000")
    (.setIgnoreRepaint true)))

(defn create-frame [screen]
  (doto (seesaw/frame :width 640
                  :height 480
                  :content screen)
    (.setIgnoreRepaint true)))

(defn init []
  (let [screen (create-screen)]
    (seesaw/invoke-now (seesaw/show! (create-frame screen)))
    (binding [gfx/*display* (gfx-impl/->JComponentDisplay screen)]
      (engine/start))))

(defn -main [& args]
  (init))

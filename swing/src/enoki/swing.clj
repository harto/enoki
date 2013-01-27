(ns enoki.swing
  (:require [enoki.graphics.java2d :as gfx]))

(defn- create-frame []
  (doto (seesaw/frame :resizable? false)
    (.setIgnoreRepaint true)))

(defn env
  "Initialise and return a Swing-based environment."
  []
  {:display (gfx/create-display (create-frame))})

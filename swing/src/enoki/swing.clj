(ns enoki.swing
  (:require [enoki.graphics.java2d :as gfx]))

(defn env
  "Initialise and return a Swing-based environment."
  []
  {:display (gfx/create-display)})

(ns enoki.swing
  (:require [clojure.string :as str]
            [seesaw.core :as seesaw]
            [enoki.graphics.java2d :as gfx]
            [enoki.keyboard :as kbd]
            [enoki.util.logging]
            [enoki.util.logging-macros :as log])
  (:import [java.awt.event KeyEvent]))

;; Interpret native key events

(defn event-key
  "Transform a KeyEvent into a keyword describing the key being pressed or
   released."
  [e]
  ;; FIXME: this obviously needs to be much smarter.
  ;; It only currently only handles alphanumeric keys.
  (-> (.getKeyCode e)
      (KeyEvent/getKeyText)
      (str/lower-case)
      (keyword)))

(defn key-pressed [e]
  (kbd/enqueue-event! :key-pressed (event-key e)))

(defn key-released [e]
  (kbd/enqueue-event! :key-released (event-key e)))

(defn create-frame []
  (doto (seesaw/frame :resizable? false)
    (seesaw/listen :key-pressed #'key-pressed)
    (seesaw/listen :key-released #'key-released)
    (.setIgnoreRepaint true)))

(defn env
  "Initialise and return a Swing-based environment."
  []
  {:display (gfx/create-display (create-frame))})

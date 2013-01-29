(ns enoki.swing
  (:require [clojure.string :as str]
            [seesaw.core :as seesaw]
            [enoki.graphics.java2d :as gfx]
            [enoki.keyboard :as kbd]
            [enoki.util.logging]
            [enoki.util.logging-macros :as log])
  (:import [java.awt.event KeyEvent]))

(def ^:private key-names
  (merge kbd/default-key-names
         {10 :enter
          44 :comma
          45 :minus
          46 :period
          47 :slash
          59 :semicolon
          61 :equals
          91 :open-bracket
          92 :backslash
          93 :close-bracket
          157 :meta}))

(defn- handle-key-event [event-type e]
  (if-let [key-name (get key-names (.getKeyCode e))]
    (kbd/enqueue-event! event-type key-name)
    (log/debug "unprocessable key event (%s): %s" event-type e)))

(defn create-frame []
  (doto (seesaw/frame :resizable? false)
    (seesaw/listen :key-pressed (partial handle-key-event :key-pressed))
    (seesaw/listen :key-released (partial handle-key-event :key-released))
    (.setIgnoreRepaint true)))

(defn env
  "Initialise and return a Swing-based environment."
  []
  {:display (gfx/create-display (create-frame))})

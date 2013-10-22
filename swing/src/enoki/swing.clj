(ns enoki.swing
  (:require [clojure.string :as str]
            [seesaw.core :as seesaw]
            [enoki.event :as event]
            [enoki.graphics.java2d :as gfx]
            [enoki.logging]
            [enoki.logging-macros :as log]))

(defn- map-char-range [from-char to-char]
  (into {} (for [code (range (int from-char)
                             (inc (int to-char)))
                 :let [identifier (-> (char code) (str/lower-case) (keyword))]]
             [code identifier])))

(def ^:private key-names
  "Map keycodes to keynames."
  (merge (map-char-range \A \Z)
         (map-char-range \0 \9)
         ;; Function keys (F1-F12)
         (zipmap (range 112 124)
                 (for [i (range 12)] (keyword (str "f" (inc i)))))
         ;; FIXME: The commented-out mappings are only available on a full-sized
         ;; keyboard, and thus haven't been tested yet.
         ;; Also TODO: numpad keys.
         {8 :backspace
          9 :tab
          10 :enter
          16 :shift
          17 :control
          18 :alt
          ;;19 :pause
          ;;20 :caps-lock
          27 :escape
          32 :space
          ;;33 :page-up
          ;;34 :page-down
          ;;35 :end
          ;;36 :home
          37 :left
          38 :up
          39 :right
          40 :down
          44 :comma
          45 :minus
          46 :period
          47 :slash
          59 :semicolon
          61 :equals
          91 :open-bracket
          92 :backslash
          93 :close-bracket
          ;;144 :num-lock
          ;;145 :scroll-lock
          157 :meta
          192 :backquote
          222 :quote}))

(defn- handle-key-event [event-type e]
  (if-let [key-name (get key-names (.getKeyCode e))]
    (event/enqueue! event-type key-name)
    (log/debug (format "unprocessable key event (%s): %s" event-type e))))

(defn create-frame []
  (doto (seesaw/frame :resizable? false)
    (seesaw/listen :key-pressed (partial handle-key-event :key-pressed))
    (seesaw/listen :key-released (partial handle-key-event :key-released))
    (.setIgnoreRepaint true)))

(defn env
  "Initialise and return a Swing-based environment."
  []
  {:display (gfx/create-display (create-frame))})

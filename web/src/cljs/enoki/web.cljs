(ns enoki.web
  (:require [clojure.browser.repl :as repl]
            [enoki.graphics.canvas :as gfx]
            [enoki.keyboard :as kbd]
            [enoki.logging :as logging]
            [enoki.macro-requirements])
  (:require-macros [enoki.logging-macros :as log]))

(def key-names
  (merge kbd/default-key-names
         {13 :enter
          ;45 :insert
          46 :delete
          91 :meta
          188 :comma
          190 :period
          191 :slash
          ;; FIXME: Opera maps 219 to :meta
          219 :open-bracket
          220 :backslash
          221 :close-bracket
          ;; Gloss over browser incompatibilities. See
          ;; http://www.javascripter.net/faq/keycodes.htm
          59 :semicolon, 186 :semicolon
          61 :equals, 107 :equals, 187 :equals
          109 :minus, 189 :minus}))

(defn handle-key-event [event-type e]
  (if-let [key-name (get key-names (.-keyCode e))]
    (do
      (kbd/enqueue-event! event-type key-name)
      (.preventDefault e))
    (log/info (format "unprocessable key event (%s): %s" event-type (.-keyCode e)))))

(defn capture-key-events! [element]
  (doto element
    (.addEventListener "keydown" (partial handle-key-event :key-pressed))
    (.addEventListener "keyup" (partial handle-key-event :key-released))))

(defn init! []
  (repl/connect "http://localhost:9000/repl")
  (logging/init!)
  ;; FIXME: this should probably come from some configuration
  (logging/set-level! (logging/root-logger) :warn)
  (logging/set-level! (logging/get-logger "enoki") :debug)
  (logging/set-level! (logging/get-logger "game") :debug))

(defn env
  "Initialise and return a web-based environment."
  [canvas-element]
  (capture-key-events! js/window)
  {:display (gfx/->CanvasDisplay canvas-element)})

(ns enoki.web
  (:require [clojure.browser.repl :as repl]
            [clojure.string :as str]
            [enoki.event :as event]
            [enoki.graphics.canvas :as gfx]
            [enoki.keyboard :as kbd]
            [enoki.logging :as logging])
  (:require-macros [enoki.logging-macros :as log]))

;; We have to deal in strings, not chars, since ClojureScript doesn't have
;; character literals or a (char) function.

(defn char->int [s]
  (.charCodeAt s 0))

(defn int->char [i]
  (.fromCharCode js/String i))

(defn map-char-range [from-char to-char]
  (into {} (for [code (range (char->int from-char)
                             (inc (char->int to-char)))
                 :let [identifier (-> (int->char code) (str/lower-case) (keyword))]]
             [code identifier])))

(def key-names
  (merge (def default-key-names
  "Platform-independent mappings of keycodes to keynames."
  (merge (map-char-range "A" "Z")
         (map-char-range "0" "9")
         ;; Function keys (F1-F12)
         (zipmap (range 112 124) (for [i (range 12)] (keyword (str "f" (inc i)))))
         ;; FIXME: The commented-out mappings are only available on a full-sized
         ;; keyboard, and thus haven't been tested yet.
         ;; Also TODO: numpad keys.
         {8 :backspace
          9 :tab
          13 :enter
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
          ;;45 :insert
          46 :delete
          91 :meta
          ;;144 :num-lock
          ;;145 :scroll-lock
          188 :comma
          190 :period
          191 :slash
          192 :backquote
          ;; FIXME: Opera maps 219 to :meta
          219 :open-bracket
          220 :backslash
          221 :close-bracket
          222 :quote
          ;; Gloss over browser incompatibilities. See
          ;; http://www.javascripter.net/faq/keycodes.htm
          59 :semicolon, 186 :semicolon
          61 :equals, 107 :equals, 187 :equals
          109 :minus, 189 :minus}))))

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

(ns enoki.keyboard
  (:require [clojure.string :as str]))

;; We have to deal in strings, not chars, since ClojureScript doesn't have
;; character literals or a (char) function.

(defn ^:clj char->int [s]
  (int (first s)))
(defn ^:clj int->char [i]
  (str (char i)))

(defn ^:cljs char->int [s]
  (.charCodeAt s 0))
(defn ^:cljs int->char [i]
  (.fromCharCode js/String i))

(defn map-char-range [from to]
  (into {} (for [i (range (char->int from) (inc (char->int to)))]
             [i (keyword (str/lower-case (int->char i)))])))

(def default-key-names
  "Platform-independent mappings of keycodes to keynames."
  (merge (map-char-range "a" "z")
         (map-char-range "A" "Z")
         (map-char-range "0" "9")
         ;; Function keys (F1-F12)
         (zipmap (range 112 124) (for [i (range 12)] (keyword (str "f" (inc i)))))
         ;; FIXME: The commented-out mappings are only available on a full-sized
         ;; keyboard, and thus haven't been tested yet.
         ;; Also TODO: numpad keys.
         {8 :backspace
          9 :tab
          16 :shift
          17 :control
          18 :alt
          ;19 :pause
          ;20 :caps-lock
          27 :escape
          32 :space
          ;33 :page-up
          ;34 :page-down
          ;35 :end
          ;36 :home
          37 :left
          38 :up
          39 :right
          40 :down
          ;144 :num-lock
          ;145 :scroll-lock
          192 :backquote
          222 :quote}))

(def event-queue
  "Stores key events as they occur. The main loop broadcasts all events in this
   queue each tick, then resets the queue for the next tick. It's up to
   implementations to capture key events and enter them into this queue."
  (atom []))

(defn enqueue-event!
  "Record a key event for later consumption, where both `event-type` and `key`
   are keywords."
  [event-type key]
  {:pre [(event-type #{:key-pressed :key-released})]}
  (swap! event-queue conj [event-type key]))

(defn consume-events!
  "Return enqueued events and reset the queue."
  []
  (let [events @event-queue]
    (reset! event-queue [])
    events))

(defn currently-pressed-keys
  "Given a key event queue (as returned by `consume-events!`) and the set of
   keys being pressed at the end of the last tick, produce the new set of keys
   currently being pressed."
  [pressed-keys event-queue]
  (reduce (fn [pressed [event-type key-name]]
            (condp = event-type
              :key-pressed (conj pressed key-name)
              :key-released (disj pressed key-name)))
          (or pressed-keys #{})
          event-queue))

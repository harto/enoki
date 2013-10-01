;; A simple eventing mechanism for subscribing to and generating events.
;;
;; Event types are identified by keywords. Registered handlers are functions
;; called with the current game state and any event-specific arguments. Handlers
;; should generally return the updated state, though return values may be
;; disregarded for some types of event (e.g. `:render`).

(ns enoki.event
  (:require [enoki.util :as util]))

(def subscribers
  "Map event types to subscribers (functions)"
  (atom {}))

(defn subscribe!
  "Register a handler function for all events of a specified type."
  [type handler]
  (swap! subscribers update-in [type] conj handler))

(defn broadcast
  "Broadcast an event of a given type to all subscribers."
  [state type & args]
  (reduce (fn [state handler] (apply handler state type args))
          state
          (@subscribers type)))

(defn broadcast-all
  "Broadcast a seq of events, each a pair of type and args."
  [state events]
  (reduce (fn [state [type args]] (apply broadcast state type args))
          state
          events))

;; ## Out-of-band events
;;
;; Environment-specific events (e.g. key presses, window focus changes) can
;; occur at any time. We need to deal with events at a predictable time during
;; the main loop. Therefore, implementations enqueue these out-of-band events
;; for subsequent re-broadcast.

(def pending
  "Out-of-band event queue, containing `[type args]` pairs."
  (atom (util/queue)))

(defn enqueue!
  "Enqueue an event for subsequent rebroadcast."
  [type & args]
  (swap! pending conj [type args]))

(defn drain!
  "Drain the pending event queue."
  []
  (let [events @pending]
    (reset! pending (util/queue))
    events))

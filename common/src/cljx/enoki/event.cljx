;; vim:syntax=clojure filetype=clojure

;; A simple eventing mechanism for subscribing to and generating events.
;;
;; Event types are identified by keywords. Registered handlers are functions
;; called with the current game state and any event-specific arguments. Handlers
;; should generally return the updated state, though return values may be
;; disregarded for some types of event (e.g. `:render`).

(ns enoki.event)

(def subscribers
  "Map event types to subscribers (functions)"
  (atom {}))

(defn subscribe!
  "Register a handler function for all events of a specified type."
  [type handler]
  (swap! subscribers update-in [type] conj handler))

(defn broadcast
  "Broadcast an event of a given type to all subscribers."
  [type state & args]
  (reduce (fn [state handler] (apply handler type state args))
          state
          (@subscribers type)))

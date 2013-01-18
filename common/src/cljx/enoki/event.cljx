;; A simple eventing mechanism for subscribing to and generating events.

(ns enoki.event)

(def subscribers
  "Map of event types to subscribers (functions)"
  (atom {}))

(defn subscribe!
  "Register `handler' for events of `type'. Event handlers should accept state,
   type and an event-specific number of arguments and must return an updated
   state."
  [type handler]
  (swap! subscribers update-in [type] conj handler))

(defn broadcast
  "Broadcast an event identified by `type' to all subscribers."
  [state type & args]
  (reduce (fn [state handler] (apply handler state type args))
          state
          (@subscribers type)))

;; A simple eventing mechanism for subscribing to and generating events.

(ns enoki.event)

(def subscribers
  "Map of event types to subscribers (functions)"
  (atom {}))

(defn subscribe! [type handler]
  (swap! subscribers update-in [type] conj handler))

(defn broadcast [state type & args]
  (reduce (fn [state handler] (apply handler state args))
          state
          (@subscribers type)))

(ns enoki.keyboard)

(def event-queue
  "Stores key events as they occur. The main loop broadcasts all events in this
   queue each tick, then resets the queue for the next tick. It's up to
   implementations to capture key events and enter them into this queue."
  (atom []))

(defn enqueue-event!
  "Record a key event for later consumption, where both `event-type` and `key`
   are keywords."
  [event-type key-name]
  {:pre [(event-type #{:key-pressed :key-released})]}
  (swap! event-queue #(conj % [event-type key-name])))

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

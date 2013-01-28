(ns enoki.keyboard)

(def event-queue
  "Stores key events as they occur. The main loop broadcasts all events in this
   queue each tick, then resets the queue for the next tick. It's up to
   implementations to capture key events and enter them into this queue."
  (atom []))

(def pressing
  "The set of keys being pressed at this moment. Note that this isn't a reliable
   way to test for a keystroke, since the user might press and release the key
   between ticks."
  (atom #{}))

(defn enqueue-event!
  "Record a key event for later consumption, where both `event-type` and `key`
   are keywords."
  [event-type key]
  {:pre [(event-type #{:key-pressed :key-released})]}
  (swap! event-queue conj [event-type key])
  (let [op (event-type {:key-pressed conj :key-released disj})]
    (swap! pressing op key)))

(defn consume-events!
  "Return enqueued events and reset the queue."
  []
  (let [events @event-queue]
    (reset! event-queue [])
    events))

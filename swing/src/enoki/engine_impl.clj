(ns enoki.engine-impl)

(defn loop-forever
  "A naïve game loop implementation that sleeps for 1 millisecond between
   calls to `tick'."
  [tick initial-state]
  (loop [state initial-state]
    (Thread/sleep 1)
    (recur (tick state))))

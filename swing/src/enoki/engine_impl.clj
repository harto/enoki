(ns enoki.engine-impl)

(defn loop-forever
  "A naïve game loop implementation that calls `tick' as often as possible."
  [tick env]
  (loop [env env]
    (Thread/yield)
    (recur (tick env))))

;; Core engine functionality.

^:clj (ns enoki.engine
        (:require [enoki.event :as e]
                  [enoki.graphics :as g]
                  [enoki.util.logging] ; required for dependency resolution
                  [enoki.util.logging-macros :as log]
                  ))

^:cljs (ns enoki.engine
         (:require [goog.Timer :as timer]
                   [enoki.event :as e]
                   [enoki.graphics :as g]
                   [enoki.util.logging] ; required for dependency resolution
                   )
         (:require-macros [enoki.util.logging-macros :as log]))

(defn update [state]
  state)

(defn tick [{:keys [state display] :as env}]
  (g/render display (fn [ctx] (e/broadcast :render state ctx)))
  (assoc-in env [:state] (update state)))

;; ## Game loop
;;
;; We need to yield control of the CPU every tick to allow IO events to be
;; generated, to play nice with other processes, etc. On the JVM we can just
;; put the running thread to sleep momentarily. The JavaScript equivalent is to
;; set a timeout that calls the next tick a moment into the future.

(defn ^:clj loop-forever
  "A naïve game loop implementation that repeatedly calls `tick', yielding
   for 1ms between calls."
  [tick env]
  (loop [env env]
    (Thread/sleep 1)
    (recur (tick env))))

(defn ^:cljs loop-forever [tick env]
  "A naïve game loop implementation that repeatedly calls `tick', yielding
   for 1 ms between calls."
  (timer/callOnce #(loop-forever tick (tick env)) 1))

(defn start
  "Enters the game loop. This function might return immediately or once the game
   loop is exited, depending on the implementation of loop-fn."
  [env]
  (g/init-display! (:display env))
  (log/info "Entering game loop")
  (loop-forever tick env))

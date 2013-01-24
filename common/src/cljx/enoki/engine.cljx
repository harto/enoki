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

(defn update
  "Trigger an update of the game state. All handler functions registered for
   the `:update` event are called with the current game state, and each must
   return an updated state."
  [state]
  (e/broadcast :update (assoc-in state [:ticks] (inc (:ticks state 0)))))

(defn render
  "Trigger a render of the current game state on a given display. Handler
   functions registered for the `:render` event are called with the state
   and a graphics context (`enoki.graphics.Context`).

   It's usually only useful to register a single handler for `:render`, as
   handlers are invoked in an unspecified order."
  [state display]
  (g/render display (fn [ctx] (e/broadcast :render state ctx))))

(defn tick [{:keys [state display] :as env}]
  (render state display)
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
  [env]
  ;; We start a new thread to aid interactive development. This returns control
  ;; to the REPL immediately.
  (-> #(loop [env env]
         (Thread/sleep 1)
         (recur (tick env)))
      (Thread.)
      (.start)))

(defn ^:cljs loop-forever [env]
  "A naïve game loop implementation that repeatedly calls `tick', yielding
   for 1 ms between calls."
  (timer/callOnce #(loop-forever (tick env)) 1))

(defn start
  "Enters the game loop. This function might return immediately or once the game
   loop is exited, depending on the implementation of loop-fn."
  [env]
  (g/init-display! (:display env))
  (log/info "Entering game loop")
  (loop-forever env))

;; Core engine functionality.

^:clj (ns enoki.engine
        (:require [enoki.event :as e]
                  [enoki.graphics :as g]
                  [enoki.keyboard :as kbd]
                  [enoki.logging] ; required for dependency resolution
                  [enoki.logging-macros :as log])
        (:use [enoki.core :only [now]]))

^:cljs (ns enoki.engine
         (:require [goog.Timer :as timer]
                   [enoki.event :as e]
                   [enoki.graphics :as g]
                   [enoki.keyboard :as kbd]
                   [enoki.logging]) ; required for dependency resolution
         (:require-macros [enoki.logging-macros :as log])
         (:use [enoki.core :only [now]]))

(defn fire-key-events [state events]
  (reduce (fn [state [event-type key]]
            (e/broadcast state event-type key))
          state
          events))

(defn handle-key-input [state key-events]
  (-> state
      (update-in [:pressed-keys] kbd/currently-pressed-keys key-events)
      (fire-key-events key-events)))

(defn update
  "Trigger an update of the game state. All handler functions registered for
   the `:update` event are called with the current game state, and each must
   return an updated state."
  [state]
  (-> state
      (handle-key-input (kbd/consume-events!))
      (e/broadcast :update)))

(defn record-tick-duration
  [{:keys [last-sample-time last-sec-ticks curr-sec-ticks] :as state} tick]
  (if (< 1000 (- (now) (or last-sample-time 0)))
    (assoc state
      :last-sample-time (now)
      :last-sec-ticks curr-sec-ticks
      :curr-sec-ticks [tick])
    (update-in state [:curr-sec-ticks] conj tick)))

(defn fps
  "Count the number of ticks from the last second."
  [state]
  (count (:last-sec-ticks state)))

(defn render
  "Trigger a render of the current game state on a given display. Handler
   functions registered for the `:render` event are called with the state
   and a graphics context (`enoki.graphics.Context`).

   It's usually only useful to register a single handler for `:render`, as
   handlers are invoked in an unspecified order."
  [state display]
  (g/render display (fn [ctx] (e/broadcast state :render ctx))))

(defn tick [{:keys [state display] :as env}]
  (let [tick-start (now)]
    (render state display)
    (-> env
        (update-in [:state] update)
        (update-in [:state] record-tick-duration (- (now) tick-start)))))

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
  (loop-forever (assoc-in env [:state :ticks] 0)))

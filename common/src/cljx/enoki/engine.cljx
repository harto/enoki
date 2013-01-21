;; Core engine functionality

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

;; Environment-specific loop functions

(defn ^:clj loop-forever
  "A naïve game loop implementation that calls `tick' as often as possible."
  [tick env]
  (loop [env env]
    (Thread/sleep 1)
    (recur (tick env))))

(defn ^:cljs loop-forever [tick env]
  (timer/callOnce #(loop-forever tick (tick env)) 1))

(defn start
  "Enters the game loop. This function might return immediately or once the game
   loop is exited, depending on the implementation of loop-fn."
  [env]
  (g/init-display! (:display env))
  (log/info "Entering game loop")
  (loop-forever tick env))

(ns enoki.keyboard
  (:require [enoki.event :as event])
  (:use [enoki.util :only [queue]]))

;; There are two ways for games to handle key input. Firstly, they can react to
;; the `:key-pressed` and `:key-released` events, which are broadcast with a key
;; identifier like `:a`, `:b`, `:c`, `:meta`, etc. Secondly, they can interrogate
;; the `:pressed-keys` property of the game state, which is a set of key
;; identifiers like `#{:a, :b, :c, :meta}`.

;; We collect key-pressed and key-released events here for subsequent collation
;; into the set of pressed keys.

(def ^:private current-tick-key-events
  (atom (queue)))

(defn ^:private handle-key-event [state event-type key-name]
  (swap! current-tick-key-events conj [event-type key-name])
  state)

(defn update-pressed-keys
  "Determine the set of keys \"currently\" being pressed, i.e. those that
   generated a `:key-pressed` event without a corresponding `:key-released`
   event, or those still pressed from the previous tick."
  [previously-pressed-keys key-events]
  (reduce (fn [pressed [event-type key-name]]
            (condp = event-type
              :key-pressed (conj pressed key-name)
              :key-released (disj pressed key-name)))
          (or previously-pressed-keys #{})
          key-events))

(defn ^:private update-currently-pressed-keys
  [state event-type]
  (let [key-events @current-tick-key-events]
    (reset! current-tick-key-events (queue))
    (update-in state [:pressed-keys] update-pressed-keys key-events)))

(defn init! []
  (event/subscribe! :key-pressed handle-key-event)
  (event/subscribe! :key-released handle-key-event)
  (event/subscribe! :update update-currently-pressed-keys))

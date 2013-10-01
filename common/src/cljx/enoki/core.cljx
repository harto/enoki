;; Various essential utilities.

(ns enoki.core)

;; FIXME: this should be false by default
(def debug? (atom true))

#+clj
(defn now
  "Return current timestamp in milliseconds."
  []
  (quot (System/nanoTime) 1000000))

#+cljs
(defn now
  "Return current timestamp in milliseconds."
  []
  (.getTime (new js/Date)))

;; Various essential utilities.

(ns enoki.core)

;; FIXME: this should be false by default
(def debug? (atom true))

(defn ^:clj now
  "Return current timestamp in milliseconds."
  []
  (quot (System/nanoTime) 1000000))

(defn ^:cljs now
  "Return current timestamp in milliseconds."
  []
  (.getTime (new js/Date)))

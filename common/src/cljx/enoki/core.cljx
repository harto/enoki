;; Various essential utilities.

(ns enoki.core)

;; FIXME: this should be false by default
(def debug? (atom false))

(defn ^:clj now
  "Return current timestamp in milliseconds."
  []
  (quot (System/nanoTime) 1e6))

(defn ^:cljs now
  "Return current timestamp in milliseconds."
  []
  (.getTime (new js/Date)))

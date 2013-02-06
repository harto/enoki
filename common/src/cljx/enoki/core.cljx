;; Various essential utilities.

(ns enoki.core)

(defn ^:clj now
  "Return current timestamp in milliseconds."
  []
  (quot (System/nanoTime) 1e6))

(defn ^:cljs now
  "Return current timestamp in milliseconds."
  []
  (.getTime (new js/Date)))

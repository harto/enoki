(ns enoki.util.logging
  (:require [goog.debug.Console]
            [goog.debug.Logger.Level :as Level]
            [goog.debug.LogManager :as LogManager]))

(def ^:private levels
  {:debug Level/FINE
   :info Level/INFO
   :warn Level/WARNING
   :error Level/SEVERE})

(defn root-logger []
  (LogManager/getRoot))

;; (defn logger [name]
;;   (LogManager/getLogger name))

(defn set-level!
  ([level]
     (set-level! (root-logger) level))
  ([logger level]
     (if-let [level (levels level)]
       (.setLevel logger level))))

;; ## Logging handlers

;; (def handlers (atom nil))

;; (defn add-handler
;;   "Register a logging handler of the form
;;    (fn [level logger-name time message])"
;;   [f]
;;   (swap! handlers conj f))

;; (defn- handler-delegate
;;   "Unpack a goog.debug.LogRecord and send it to all handler functions."
;;   [record]
;;   (let [level (.getLevel record)
;;         name (.getLoggerName record)
;;         time (.getMillis record)
;;         message (.getMessage record)]
;;     (doseq [handler @handlers]
;;       (handler level name time message))))

;; ## Logging functions

(defn log* [level message-fn]
  (let [level (levels level)
        ;; FIXME: accept logger (name?) as param
        logger (root-logger)]
    (if (.isLoggable logger level)
      (.log logger level (str (message-fn))))))

;; ## Initialisation

(defn init! []
  (.setCapturing (goog.debug.Console.) true)
  ;; (.addHandler (root-logger) handler-delegate)
  )

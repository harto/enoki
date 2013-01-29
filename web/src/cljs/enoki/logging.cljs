;; A web-specific logging implementation that defers to the GClosure logging
;; subsystem.

(ns enoki.logging
  (:require [goog.debug.Console]
            [goog.debug.Logger.Level :as Level]
            [goog.debug.LogManager :as LogManager]))

(def ^:private goog-level
  "Maps keywords to goog.debug.Logger.Levels."
  {:debug Level/FINE
   :info Level/INFO
   :warn Level/WARNING
   :error Level/SEVERE})

(defn root-logger []
  (LogManager/getRoot))

(defn get-logger [logger-name]
  (LogManager/getLogger logger-name))

(defn set-level!
  ([level]
     (set-level! (root-logger) level))
  ([logger level]
     (.setLevel logger (goog-level level))))

;; ## Logging functions

(defn log* [logger-name level message-fn]
  (let [level (goog-level level)
        logger (get-logger logger-name)]
    (if (.isLoggable logger level)
      (.log logger level (str (message-fn))))))

;; ## Initialisation

(defn init! []
  (.setCapturing (goog.debug.Console.) true))

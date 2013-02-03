;; A set of macros that defer to implementation-specific logging functions.
;;
;; Implementations must provide the `enoki.logging` namespace, and define a
;; logging function within that namespace that looks like:
;;
;;     (fn log* [logger-name level eval-msg-fn])
;;
;; Messages are not evaluated unless the message priority exceeds the relevant
;; logging level.

(ns enoki.logging-macros)

(defmacro log
  "Delegates logging to function in implementation-defined namespace."
  [level msg]
  `(enoki.logging/log* ~(str *ns*) ~level #(str ~msg)))

(defmacro debug [msg]
  `(log :debug ~msg))

(defmacro info [msg]
  `(log :info ~msg))

(defmacro warn [msg]
  `(log :warn ~msg))

(defmacro error [msg]
  `(log :error ~msg))

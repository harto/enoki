;; A set of macros that defer to implementation-specific logging functions.
;; Messages are not evaluated unless the message priority exceeds the relevant
;; logging level.

(ns enoki.logging-macros)

(defmacro log
  "Delegates logging to function in implementation-defined namespace."
  [level msg args]
  `(enoki.logging/log* ~(str *ns*) ~level #(format (str ~msg) ~@args)))

(defmacro debug [msg & args]
  `(log :debug ~msg ~args))

(defmacro info [msg & args]
  `(log :info ~msg ~args))

(defmacro warn [msg & args]
  `(log :warn ~msg ~args))

(defmacro error [msg & args]
  `(log :error ~msg ~args))

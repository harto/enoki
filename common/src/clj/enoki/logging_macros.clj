;; A set of macros that defer to implementation-specific logging functions.
;;
;; Implementations must provide the `enoki.logging` namespace, and define a
;; logging function within that namespace that looks like:
;;
;;     (fn log* [logger-name level eval-msg-fn throwable])
;;
;; Messages are not evaluated unless the message priority exceeds the relevant
;; logging level.

(ns enoki.logging-macros)

(defmacro log
  "Delegates logging to function in implementation-defined namespace."
  ([level msg]
     `(log ~level ~msg nil))
  ([level msg throwable]
     `(enoki.logging/log* ~(str *ns*) ~level #(str ~msg) ~throwable)))

(defmacro debug
  ([msg] `(debug ~msg nil))
  ([msg throwable] `(log :debug ~msg ~throwable)))

(defmacro info
  ([msg] `(info ~msg nil))
  ([msg throwable] `(log :info ~msg ~throwable)))

(defmacro warn
  ([msg] `(warn ~msg nil))
  ([msg throwable] `(log :warn ~msg ~throwable)))

(defmacro error
  ([msg] `(error ~msg nil))
  ([msg throwable] `(log :error ~msg ~throwable)))

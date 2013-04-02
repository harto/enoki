(ns enoki.error-macros)

(defmacro signal-error
  "Throw an error if we're in dev, but just log it if we're in production."
  ([msg]
     `(signal-error ~msg nil))
  ([msg throwable]
     `(if @enoki.core/debug?
        (throw (enoki.error/error ~msg ~throwable))
        (enoki.logging-macros/error ~msg ~throwable))))

;; ClojureScript macros don't live in the same namespace as ClojureScript
;; functions. This causes a dependency problem. Suppose we have a namespace
;; `foo-macros`:
;;
;;     (ns foo-macros)
;;
;;     (defmacro bar []
;;       `(some.ns/some-function))
;;
;; Then suppose we want to use this macro from a ClojureScript namespace `foo`:
;;
;;     (ns foo
;;       (:require-macros [foo-macros :as fm]))
;;
;;     (fm/bar)
;;
;; This code fails at runtime, because `foo` hasn't required `some.ns`. This is
;; annoying, because wherever we require `foo-macros` we must also require
;; `some.ns`.
;;
;; If ClojureScript implemented run-time `(require)` we could insert relevant
;; clauses in the macroexpanded code (e.g.
;; `(foo-macros/bar) => (do (require 'some.ns) (some.ns/some-function))`).
;;
;; Unfortunately it doesn't, and so the kludgey solution implemented here is to
;; simply require all namespaces required by all macroexpanded code :-(
;;
;; This file only needs to be included once, near the start of your program
;; (i.e. probably in the namespace that defines your program's entry point).

(ns enoki.macro-requirements
  (:require [enoki.core]
            [enoki.error]
            [enoki.logging]))

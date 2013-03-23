This is a proof-of-concept game engine that is mostly written in the common
subset of Clojure and ClojureScript. Theoretically it could target any JS or
Clojure platform (e.g. Android). It currently only targets the browser and Java
desktop.

 * `common` contains platform-independent sources.
 * `web` and `swing` contain platform-specific bindings.
 * `example` contains an example game implementation.

Eventually each of these subdirectories will be split into its own repo.

[![Build status](https://travis-ci.org/harto/enoki.png)](https://travis-ci.org/harto/enoki)

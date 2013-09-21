Enoki
=====

Enoki is a proof-of-concept functional game engine, mostly written in a common
subset of Clojure and ClojureScript. Theoretically it could target any JS or
Clojure platform (e.g. Android). It currently targets the browser and Java
desktop environments.

[![Build status](https://travis-ci.org/harto/enoki.png)](https://travis-ci.org/harto/enoki)

The bulk of Enoki is implemented in the annotated [cljx][cljx] format.

 * `common` contains platform-independent sources. Most of this is cljx code.
 * `web` and `swing` contain platform-specific implementations.

Each of these subdirectories will eventually live in its own repo.

[See the wiki][wiki] for high-level documentation.


 [cljx]: https://github.com/lynaghk/cljx
 [wiki]: https://github.com/harto/enoki/wiki

This is a proof-of-concept game engine that is mostly written in the common
subset of Clojure and ClojureScript. Theoretically it could target any JS or
Clojure platform (e.g. Android). It currently only targets the browser and Java
desktop.

`common` contains platform-independent source, assets, etc.

`web` and `swing` contain platform-specific bindings.

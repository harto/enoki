This is a browser-based game implementation. It includes a simple Ring
application that serves the ClojureScript environment.

To run the server, try `lein do cljx, cljsbuild once, run`, then visit
http://localhost:8080/.

The browser will automatically attempt to connect to a ClojureScript REPL. You
can start one using something like `lein trampoline cljsbuild repl-listen`. If
you're doing interactive development, you might also investigate `lein cljx
auto` and `lein cljsbuild auto`.

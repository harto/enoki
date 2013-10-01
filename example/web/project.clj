(defproject game-web "0.0.1-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.5.0"]
                 [ring "1.1.6"]
                 [compojure "1.1.3"]
                 [enlive "1.0.0"]]

  :plugins [[com.keminglabs/cljx "0.3.0"]
            [lein-cljsbuild "0.3.0"]]

  :source-paths ["src/clj"]

  :test-paths ["test/cljs"
               "generated/test"]

  :cljx {:builds [{:source-paths ["../common/src/cljx"
                                  ;; FIXME: remove once engine implementations
                                  ;; are split into separate repos
                                  "../../common/src/cljx"]
                   :output-path "generated/src"
                   :rules :cljs}]}

  :cljsbuild {:builds [{:source-paths ["src/cljs"
                                       "generated/src"
                                       ;; FIXME: remove once engine implementations
                                       ;; are split into separate repos
                                       "../../common/src/clj"
                                       "../../web/src/cljs"]
                        :compiler {:output-to "resources/public/js/main.js"}}]}

  :aliases {"cljs-repl" ["trampoline" "cljsbuild" "repl-rhino"]}

  :main game.web.server)

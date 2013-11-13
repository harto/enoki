(defproject enoki-web "0.0.1-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.5.0"]]

  :plugins [[com.keminglabs/cljx "0.3.0"]
            [lein-cljsbuild "0.3.0"]
            [lein-marginalia "0.7.1"]]

  :profiles {:dev {:dependencies [[com.cemerick/clojurescript.test "0.0.2"]]}}

  :source-paths ["src/cljs"
                 "generated/src"]

  ;; :test-paths ["generated/test"]

  ;; FIXME: update once implementations are separated from core
  :cljx {:builds [{:source-paths ["../common/src/cljx"]
                   :output-path "generated/src"
                   :rules :cljs}

                  {:source-paths ["../common/test/cljx"]
                   :output-path "generated/test"
                   :rules :cljs}]}

  :cljsbuild {:builds [{:source-paths ["src/cljs"
                                       "generated/src"
                                       "../common/src/clj"
                                       "generated/test"]
                        :compiler {:output-to "target/cljs/test-suite.js"}}]
              :test-commands {"unit-tests" ["test/test-runner.js" "target/cljs/test-suite.js"]}}

  :aliases {"citest" ["do" "cljx," "cljsbuild" "once," "cljsbuild" "test"]
            "docs" ["do" "cljx," "marg" "src/cljs" "generated/src" "../common/src/clj"]})

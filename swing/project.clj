(defproject game-swing "0.0.1-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.logging "0.2.4"]
                 [seesaw "1.4.2"]]

  :plugins [[com.keminglabs/cljx "0.2.0"]]

  :profiles {:dev {:dependencies [[log4j "1.2.17"]]}}

  :source-paths ["src"
                 "generated/src"
                 "../common/src/clj"]

  :test-paths ["test/clj"
               "generated/test"]

  :cljx {:builds [{:source-paths ["../common/src/cljx"]
                   :output-path "generated/src"
                   :include-meta true
                   :rules cljx.rules/clj-rules}

                  {:source-paths ["../common/test/cljx"]
                   :output-path "generated/test"
                   :include-meta true
                   :rules cljx.rules/clj-rules}]}

  :aliases {"cibuild" ["do" "cljx," "test"]}

  :main game.swing.main)

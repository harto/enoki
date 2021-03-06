(defproject game-swing "0.0.1-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/tools.logging "0.2.4"]
                 [seesaw "1.4.3"]]

  :plugins [[com.keminglabs/cljx "0.3.0"]]

  :profiles {:dev {:dependencies [[log4j "1.2.17"]]}}

  :source-paths ["src"
                 "generated/src"
                 "../../common/src/clj"
                 "../../swing/src"]

  :test-paths ["test/clj"
               "generated/test"]

  :resource-paths ["resources"
                   "../common/assets"]

  :cljx {:builds [{:source-paths ["../common/src/cljx"
                                  "../../common/src/cljx"]
                   :output-path "generated/src"
                   :rules :clj}]}

  :aliases {"cibuild" ["do" "cljx," "test"]}

  :main game.swing.main)

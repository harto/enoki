(defproject enoki-swing "0.0.1-SNAPSHOT"

  ;; FIXME: this doesn't really do anything; it only exists for CI at this stage

  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/tools.logging "0.2.4"]
                 [seesaw "1.4.2"]]

  :plugins [[com.keminglabs/cljx "0.2.1"]]

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

  :aliases {"citest" ["do" "cljx," "test"]})

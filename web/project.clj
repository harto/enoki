(defproject enoki-web "0.0.1-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.5.0"]]

  :plugins [[com.keminglabs/cljx "0.2.1"]
            [lein-cljsbuild "0.3.0"]]

  :source-paths ["src/clj"]

  :test-paths ["test/cljs"
               "generated/test"]

  ;; FIXME: update once implementations are separated from core
  :cljx {:builds [{:source-paths ["../common/src/cljx"]
                   :output-path "generated/src"
                   :extension "cljs"
                   :include-meta true
                   :rules cljx.rules/cljs-rules}

                  {:source-paths ["../common/test/cljx"]
                   :output-path "generated/test"
                   :extension "cljs"
                   :include-meta true
                   :rules cljx.rules/cljs-rules}]}

  ;; :cljsbuild {:builds [{:source-paths ["src/cljs"
  ;;                                      "generated/src"
  ;;                                      "../common/src/clj"]
  ;;                       :compiler {:output-to "resources/public/js/main.js"}}]}

  :aliases {"cibuild" ["do" "cljx," "cljsbuild" "once," "test"]})

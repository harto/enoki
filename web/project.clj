(defproject game-web "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojurescript "0.0-1450"]
                 [ring "1.1.6"]
                 [compojure "1.1.3"]
                 [enlive "1.0.0"]]
  :plugins [[com.keminglabs/cljx "0.2.0"]
            [lein-cljsbuild "0.2.7"]]
  :source-paths ["src/clj"
                 "../common/clj"
                 "generated"]
  :cljx {:builds [{:source-paths ["../common/cljx"]
                   :output-path "generated"
                   :extension "cljs"
                   :include-meta true
                   :rules cljx.rules/cljs-rules}]}
  :cljsbuild {:builds [{:source-path "src/cljs"
                        :compiler {:output-to "resources/public/js/main.js"}}]}
  :main game.web.server)

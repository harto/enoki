(defproject game-swing "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [seesaw "1.4.2"]]
  :plugins [[com.keminglabs/cljx "0.2.0"]]
  :source-paths ["src"
                 "generated"
                 "../common/src/clj"]
  :cljx {:builds [{:source-paths ["../common/src/cljx"]
                   :output-path "generated"
                   :include-meta true
                   :rules cljx.rules/clj-rules}]}
  :main game.swing.main)

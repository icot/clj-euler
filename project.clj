(defproject org.clojars.icot/euler "0.3.1-SNAPSHOT"
  :description "Mathematical functions intended to help in solving Project Euler problems"
  :url "http://github.com/icot/clj-euler"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.trace "0.7.10"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [uncomplicate/neanderthal "0.38.0"]
                 [criterium "0.4.6"] ; benchmarking
                 [org.clojure/core.async "1.3.610"]
                 [com.clojure-goes-fast/clj-async-profiler "0.5.0"] ; FlameGraphs
                 [org.clojure/java.jdbc "0.7.11"]
                 [org.xerial/sqlite-jdbc "3.34.0"]
                 [ubergraph "0.8.2"]]
                 
  ;; deploy-repositories setup to do `lein deploy clojars` on `lein deploy`
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :repl-options {:init-ns euler.core}
  ; Options required to use clj-async-profiler
  :jvm-opts ["-Djdk.attach.allowAttachSelf"
             "-XX:+UnlockDiagnosticVMOptions"
             "-XX:+DebugNonSafepoints"
             "-Xss1G"])
             

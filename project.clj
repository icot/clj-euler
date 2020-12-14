(defproject euler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.trace "0.7.10"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [uncomplicate/neanderthal "0.38.0"]
                 [criterium "0.4.6"] ; benchmarking
                 [com.clojure-goes-fast/clj-async-profiler "0.4.1"] ; FlameGraphs
                 ]
  :repl-options {:init-ns euler.core})

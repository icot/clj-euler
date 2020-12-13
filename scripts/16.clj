(require 'clojure.math.numeric-tower)
(reduce + (map #(Character/digit %1 10) (str (clojure.math.numeric-tower/expt 2 1000))))

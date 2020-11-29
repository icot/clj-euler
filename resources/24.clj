(ns euler.24)

(use 'clojure.math.combinatorics)
(time (println (last (take 1000000 (permutations (range 10))))))

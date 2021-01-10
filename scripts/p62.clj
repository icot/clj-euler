(ns p62
  (:require [euler.core :refer [digits]]))

(def naturals (iterate inc 1))
(def cubes (map #(*' % % %) naturals))

(newline)
(def my-cubes (take 10000 cubes))

(time (def result
  (as-> my-cubes input
    (for [c input] (hash-map (sort (digits c)) (vector c)))
    (apply merge-with concat input)
    (filter #(= (count (second %)) 5) input)
    (map (comp first second) input)
    (apply min input))))

(prn result)

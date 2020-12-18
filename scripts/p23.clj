(ns p23
  (:require [clojure.set :as sets])
  )

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def N 28123)

(def nums (range 1 N))

(def dn (map #(euler.core/sum (euler.primes/proper-divisors %)) nums))

(def dns (into (sorted-map) (zipmap nums dn)))

(def fdns (into (sorted-map) (filter #(> (last %) (first %)) (seq dns))))

(def abundant-numbers (keys fdns))

(def sums (into (sorted-set) (for [a abundant-numbers b abundant-numbers :when (< (+ a b) N)] (+ a b))))

; Solution 4179871
(println (euler.core/sum (seq (sets/difference (set nums) sums))))

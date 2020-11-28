(ns euler)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def N 100)
(def primes (euler.primes/sieve N))

(def nums (filter #(not (.contains primes %)) (range 1 N)))
(def dn (map #(euler.core/sum (euler.primes/proper-divisors %)) nums))

(def mapping (into (sorted-map) (map vector nums dn)))

(println mapping)
;; Process mapping

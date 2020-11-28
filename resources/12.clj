(ns 12)

(use 'euler.primes)
(time (find-first #(< 500 (num-divisors %)) (triangle-gen)))

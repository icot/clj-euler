(ns p88
  (:require [euler.primes :as ep]
            [clojure.math.combinatorics :as combo]))
  
;; combinations-with-repetition -> (combo/selections elements k)  


;; For each k in [2,12000]

;; Compute integer partitions for k where possible partitions are the proper-divisors of k
;; If we can generate partitions starting by the ones formed by the smaller elements
;; the first one we find where sum(a_k) = prod(a_k)

(def primes (concat '(1) (take-while #(< % 200) (ep/gen-primes))))

(defn mytest [s]
  (= (reduce + s)
     (reduce * s)))

;; Naive approach
(defn min-product-sum [k]
  (reduce min (let [population (range 1 (inc k))
                    cs (combo/selections population k)]
               (map #(reduce + %)(filter mytest cs)))))

(defn min-product-sum-primes [k]
  (reduce min (let [population (take-while #(< % 20) primes)
                    cs (combo/selections population k)]
                (map #(reduce + %)(filter mytest cs)))))

;; Possible mprovement:
;; start with as small selections population as possible and increment only if needed?
 

(def N (inc 6))

(time (println (reduce + (distinct (for [k (range 2 N)] (min-product-sum k))))))


(time (println (min-product-sum 5)))
(time (println (min-product-sum-primes 5)))


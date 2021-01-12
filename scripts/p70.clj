(ns p70
  (:require [euler.primes :as ep])
  (:require [clojure.math.combinatorics :as combo])
  (:require [euler.core :as ec]))

;(def primes (ep/sieve (Math/round (Math/sqrt 1000000))))
(def primes (filter #(> % 2000) (ep/sieve 5000)))
(def pdict (zipmap primes (repeat true)))

(defn gcd [a b]
  (if (> a b)
    (if (zero? b)
      a
      (recur b (mod a b)))
    (recur b a)))

(defn phi
  ([n] (reduce + (for [k (range n) :when (= (gcd n k) 1)] 1)))
  ([n primes pdict]
   (if (pdict n)
     (dec n)
     (let [dfs (into #{} (rest (ep/factorize-sieve n primes)))]
       (*' n (reduce *' (map #(- 1 (/ 1 %)) (seq dfs))))))))


(def limit 10000000)

;; N = 1e6
;; "Min N" 783169 "Min n/phi(n)" 783169/781396
;; "Elapsed time: 397649.231437 msecs"

;; N = 1e6, pdict version
;; "Min N" 783169 "Min n/phi(n)" 783169/781396
;; "Elapsed time: 188368.197536 msecs"

;; N = 1e6, butlast instead of filter
;;"Min N" 783169 "Min n/phi(n)" 783169/781396
;; "Elapsed time: 181419.077517 msecs"

;; N = 1e6, set instead of sorted-set
;; "Min N" 783169 "Min n/phi(n)" 783169/781396
;; "Elapsed time: 180321.424865 msecs"

;; vectorized factorize-sieve + rest instead of butlast
;; "Min N" 783169 "Min n/phi(n)" 783169/781396
;; "Elapsed time: 173510.016584 msecs"

;; TODO: Investigate further optimizations by adding type hints and maybe
;; further refactoring

;; Brute Force
;;
;;(prn (format "Processing to %d" limit))
;;(time
;;(loop [n 2 minn 2 min-quotient 2]
;;  (let [phi-n (phi n primes pdict)
;;        qn (/ n phi-n)
;;        permutation? (= (sort (ec/digits n)) (sort (ec/digits phi-n)))]
;;    (cond
;;      (> n limit) (prn "Min N" minn "Min n/phi(n)" min-quotient)
;;      (and (<= qn min-quotient) permutation?) (recur (inc n) n qn)
;;      :else (recur (inc n) minn min-quotient)))))

;; From observing the result for lower limits, we can see that the result
;; is always a product of two primes.
;;
;; Limiting search to the product of primes close to the SQRT(limit)

(def cs (combo/combinations primes 2))
(newline)

(prn (count cs))

(loop [c cs minn 2 min-quotient 2]
  (if (empty? c)
    (prn "Min N" minn "Min n/phi(n)" min-quotient)
    (let [p (first c)
          n (* (first p) (last p))
          totient (* (dec (first p)) (dec (last p)))
          qn (/ n totient)
          perm? (= (sort (ec/digits n)) (sort (ec/digits totient)))]
      (cond
        (and (<= qn min-quotient) (< n limit) perm?) (recur (rest c) n qn)
        :else (recur (rest c) minn min-quotient)))))

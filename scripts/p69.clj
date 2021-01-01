(ns p69
  (:require [euler.primes :as ep]))

(def primes (ep/sieve (Math/round (Math/sqrt 1000000))))

(defn gcd [a b]
  (if (> a b)
    (if (zero? b)
      a
      (recur b (mod a b)))
    (recur b a)))

(def mgcd (memoize gcd))

(defn phi
  ([n] (reduce + (for [k (range n) :when (= (gcd n k) 1)] 1)))
  ([n primes]
   (let [dfs (into (sorted-set) (filter #(> % 1)) (ep/factorize-sieve n primes))
         ]
     (*' n (reduce *' (map #(- 1 (/ 1 %)) (seq dfs)))))))

(time (prn (phi 1000000 )))
(time (prn (phi 1000000 primes)))

(time
(loop [n 1 maxn 0 max-quotient 0]
  (let [phi-n (phi n)
        qn (/ n phi-n)]
    (cond
      (> n 1000) (prn "Naive Max N" maxn "Max n/phi(n)" max-quotient)
      (> qn max-quotient) (recur (inc n) n qn)
      :else (recur (inc n) maxn max-quotient))))
)

(time
(loop [n 1 maxn 0 max-quotient 0]
  (let [phi-n (phi n primes)
        qn (/ n phi-n)]
    (cond
      (> n 1000000) (prn "Max N" maxn "Max n/phi(n)" max-quotient)
      (> qn max-quotient) (recur (inc n) n qn)
      :else (recur (inc n) maxn max-quotient))))
)

; Max N 510510: 309s
;  Pruning primes to sqrt(n) -> 11s

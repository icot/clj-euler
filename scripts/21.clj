(ns p21)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def N 10000)
(def primes (euler.primes/sieve N))

(def nums (filter #(not (.contains primes %)) (range 1 N)))
(def dn (map (comp set vector) (map #(euler.core/sum (euler.primes/proper-divisors %)) nums)))
(def dns (into (sorted-map) (zipmap nums dn)))

(defn invert-map-of-sets [m]
   (reduce (fn [a [k v]] (assoc a k (conj (get a k #{}) v))) (sorted-map) (for [[k s] m v s] [v k])))

(def idns (invert-map-of-sets dns))

;
(loop [n 1 pairs '()]
  (let [di (euler.core/sum (euler.primes/proper-divisors n))
        ddi (euler.core/sum (euler.primes/proper-divisors di))]
    (if (< n N)
      (if (and (= n ddi) (not= n di))
        (recur (inc n) (cons (list n di) pairs))
        (recur (inc n) pairs)
        )
      (do
        (println pairs)
        (println (set (map sort pairs)))
        (println (reduce + (flatten (seq (set (map sort pairs))))))
    )
    )
  )
)

(ns euler)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def N 300)
(def primes (euler.primes/sieve N))

(def nums (filter #(not (.contains primes %)) (range 1 N)))
(def dn (map #(euler.core/sum (euler.primes/proper-divisors %)) nums))

(def dn-mapping (into (sorted-map) (map vector nums dn)))
(def candidates (filter #(< % N) dn))

(defn find-amicable
  ([candidates mapping] (find-amicable candidates mapping (sorted-map)))
  ([candidates mapping pairs]
   (do
;;     (println candidates pairs)
   (let [item (first candidates)
         friends (set (filter #(= (last %) item) mapping))]
     (if (nil? item) pairs
         (recur (rest candidates) mapping (merge pairs {item friends}))
     )
    )
   )
   )
  )

(def inverse-mapping (find-amicable candidates dn-mapping))

(println (get dn-mapping 220))
(println (get inverse-mapping 280))

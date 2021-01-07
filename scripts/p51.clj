(ns p51
  (:require [euler.primes :as ep]))

(def primes (filter #(> % 50) (ep/sieve 1000)))

(def dp (zipmap primes (map #(count (str %)) primes)))

(defn invert-hash-map [h]
  (let [g (group-by val h)
        vals (map #(map first %) (vals g))]
    (zipmap (keys g) vals)))

; Generate families

(prn (invert-hash-map dp))

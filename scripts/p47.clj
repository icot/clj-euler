(ns p47
  (:require [euler.primes :as ep]))

(time
 (loop [n 0 l 0]
  (let [fs (ep/factorize-sieve n)
        nf (count (set fs))]
    (do
;      (prn n fs)
    (cond
      (>= l 4) (prn "Solution" (- n 4))
      (>= nf 5) (recur (inc n) (inc l))
      :else (recur (inc n) 0)
      )
    )
    )
  )
 )

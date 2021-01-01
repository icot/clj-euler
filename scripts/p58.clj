(ns p58
  (:require [euler.primes :as ep]))

(newline)

(prn "Pre-computing primes")
(def ph (zipmap (ep/sieve 1000000000) (repeat true)))

(prn "Starting loop")
(time
 (loop [x 3 n 1 np 0]
  (if (and (< (/ np n) 0.1) (> np 0))
    (prn "Rank: " (- x 2) "Np: " np "N: " n "Ratio:" (float (/ np n)))
    (let [d1 (*' x x)
          d2 (+' (-' d1 x) 1)
          d3 (+' (-' d1 (*' x 2)) 2)
          d4 (+' (-' d1 (*' x 3)) 3)
          inc-np (reduce + (for [d (list d1 d2 d3 d4) :when (get ph d false)] 1))
          nnp (+ np inc-np)
          nn (+ n 4)
          nx (+ x 2)]
      (do
;        (prn d1 d2 d3 d4)
;        (prn "Rank: " x "Np: " np "N: " n "Ratio:" (float (/ np n)) d1 d2 d3 d4)
      (recur nx nn nnp))))
  )
)

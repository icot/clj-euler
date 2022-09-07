(ns p77
  (:require [euler.primes :as ep]))

; https://mathworld.wolfram.com/PrimePartition.html
; https://mathworld.wolfram.com/EulerTransform.html
; https://math.stackexchange.com/questions/89240/prime-partition#:~:text=A%20prime%20partition%20of%20a,%2C%20and%20%7B5%207%7D%20.


(def primes (ep/sieve 100))

(def sopf
  (memoize (fn [k] (dec (apply + (set (ep/factorize-sieve k primes)))))))

(def prime-partitions
  (memoize
   (fn [n] (cond
;            (< n 1) 0
            (= n 1) 1
            :else (* (/ n)
                     (+ (sopf n)
                        (apply +
                               (for [j (range 1 (dec n))] (* (sopf j)
                                                             (prime-partitions (- n j)))))))))))

(newline)

(loop [n 1]
  (let [p (prime-partitions n)]
    (if (> p 5000)
      (prn (format "N: %d Pp(n): %d" n (biginteger p)))
      (recur (inc n)))))

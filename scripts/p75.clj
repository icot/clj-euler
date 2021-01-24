(ns p75
  (:require [euler.primes :as ep]))

; Using Euclid's formula (https://en.wikipedia.org/wiki/Pythagorean_triple)

(def MAX-PERIMETER 1500000)

(def LIMIT 2000)

(def perimeters (atom {}))

(time
(loop [m 1]
  (if (> m LIMIT)
    (prn (format "Total: %d" (count (filter #(= 1 (last %)) @perimeters))))
    (let [delta (loop [n 1]
                  (if (= m n)
                    nil
                    (if (not= (ep/gcd m n) 1)
                      (recur (inc n))
                      (let [m2 (* m m)
                            n2 (* n n)
                            a (- m2 n2)
                            b (* 2 m n)
                            c (+ m2 n2)
                            p (+ a b c)
                            count (get @perimeters p 0)]
                        (do
                          (if (<= p MAX-PERIMETER)
                            (swap! perimeters assoc p (inc count)))
                          (recur (inc n)))))))]
      (recur (inc m)))))
)


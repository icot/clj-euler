(ns p75
  (:require [euler.primes :as ep]))

; Using Euclid's formula (https://en.wikipedia.org/wiki/Pythagorean_triple)

(def MAX-PERIMETER 1500000)
(def LIMIT (int (Math/sqrt MAX-PERIMETER)))

(def perimeters (atom {}))

(newline)
(prn (format "P: %d Limit: %d" MAX-PERIMETER LIMIT))

(time
 (loop [m 2]
  (if (> m LIMIT)
    (do
;      (prn perimeters)
      (prn (format "Total: %d" (count (filter #(= 1 (last %)) @perimeters)))))
    (do
      (loop [n 1]
                  (if (= m n)
                    nil
                    (if (or (not= (ep/gcd m n) 1) (not= (mod (/ (+ m n) 2) 1)))
                      (recur (inc n))
                      (let [m2 (* m m)
                            n2 (* n n)
                            a (- m2 n2)
                            b (* 2 m n)
                            c (+ m2 n2)
                            p (+ a b c)
                            ps (for [k (range 1 (inc (quot MAX-PERIMETER p)))] (* p k))]
                        (do
;                          (prn ps @perimeters)
                          (loop [p' ps]
                            (if (not (empty? (rest p')))
                              (do
                                (swap! perimeters assoc (first p') (inc (get @perimeters (first p') 0)))
                                (recur (rest p')))))
                          (recur (inc n)))))))
      (recur (inc m))))
  )
 )

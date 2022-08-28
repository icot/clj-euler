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
  (if (>= m LIMIT)
    (prn (format "Total: %d" (count (filter #(= 1 (last %)) (seq @perimeters)))))
    (do
      (loop [n 1]
        (if (< n m)
            (if (and (odd? (+ m n)) (= (ep/gcd m n) 1))
                (let [m2 (* m m)
                      n2 (* n n)
                      a (- m2 n2)
                      b (* 2 m n)
                      c (+ m2 n2)
                      p (+ a b c)
                      ps (for [k (range 1 (inc (quot MAX-PERIMETER p)))] (* p k))
                      fps (frequencies ps)]
;                  (prn fps @perimeters)
                  (swap! perimeters (fn [x y] (merge-with + x y)) fps)
                  (recur (inc n)))
                (recur (inc n)))))
      (recur (inc m))))))
  
 
;; correct answer: 161667


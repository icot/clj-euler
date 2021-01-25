(ns p80
  (:require [clojure.math.numeric-tower :as nt]
            [euler.core :as ec]))


(defn isqrt ^BigInteger [^BigInteger n]
  "Computes integer square root of n following Newton's method.
   See https://en.wikipedia.org/wiki/Integer_square_root"
  (loop [x (quot n 2)]
    (let [x' (quot (+' x (quot n x)) 2)]
      (if (< (nt/abs  (-' x x')) 1)
        x'
        (recur x')))))


(newline)

(def big-two (bigint (* 2 (Math/pow 10 200))))

; Using libraries
(time
 (let [s (first (nt/exact-integer-sqrt big-two))]
  (do
    (prn (apply + (take 100 (ec/digits s)))))))

(time
 (let [s (isqrt big-two)]
  (do
    (prn (apply + (take 100 (ec/digits s)))))))

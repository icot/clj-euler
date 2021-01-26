(ns p80
  (:require [clojure.math.numeric-tower :as nt]
            [euler.core :as ec]))


(defn isqrt ^BigInteger [^BigInteger n]
  "Computes integer square root of n following Newton's method.
   See https://en.wikipedia.org/wiki/Integer_square_root"
  (loop [x (quot n 2)]
    (let [x' (quot (+' x (quot n x)) 2)
          d (- x' x)
          diff (if (neg? d) (- d) d)]
      (if (< diff 1)
        x'
        (recur x')))))


; http://www.afjarvis.staff.shef.ac.uk/maths/jarvisspec02.pdf
; BUG: Incorrect?
(defn isqrt-sub ^BigInteger [^BigInteger n ^long prec]
  (let [limit (bigint (Math/pow 10 (inc prec)))
        a0 (* 5 n)
        b0 5]
    (loop [a a0 b b0]
      (if (< b limit)
        (if (>= a b)
          (recur (- a b) (+ b 10))
          (recur (* 100 a) (+ 5 (* 100 (/ b 10))))
          )
        (quot b 100)))))

(newline)

(defn bignum [n] (bigint (* n (Math/pow 10 200))))
(def naturals (range 2 100))
(def squares (set (for [i (range 1 10)] (* i i))))

;          (apply + (take 100 (digits (nt/exact-integer-sqrt (bignum s))))))))

(time
 (prn
  (apply +
        (for [s naturals :when (not (squares s))]
          (apply + (take 100 (ec/digits (isqrt (bignum s)))))))))

; BUG: Incorrect answer
; 40821N
; "Elapsed time: 33.603206 msecs"

; (prn (isqrt-sub 2 101))

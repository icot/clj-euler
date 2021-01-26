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
(defn isqrt-sub ^BigInteger [n prec]
  (let [limit (bigint (Math/pow 10 (inc prec)))
        a0 (*' 5 n)
        b0 5]
    (loop [a a0 b b0]
      (if (< b limit)
        (if (>= a b)
          (recur (-' a b) (+' b 10))
          (recur (*' 100 a) (+' 5 (*' 100 (quot b 10))))
          )
        b))))

(newline)

(defn bignum [n] (bigint (*' n (Math/pow 10 302))))
(def naturals (range 2 100))
(def squares (set (for [i (range 1 10)] (* i i))))

;          (apply + (take 100 (digits (nt/exact-integer-sqrt (bignum s))))))))

; (prn (apply + (ec/digits (isqrt-sub 2 ))))

(time
 (prn
  (apply +
        (for [s naturals :when (not (squares s))]
          (apply + (take 100 (ec/digits (isqrt (bignum s)))))))))

; BUG: Incorrect answer, related with the exponent of 10 not being big enough
; 40821N
; "Elapsed time: 33.603206 msecs"

; (defn bignum [n] (bigint (* n (Math/pow 10 300)))) -> 40881
; (defn bignum [n] (bigint (*' n (Math/pow 10 302)))) -> 40900
;
; The method doesn't seem to be stable enough


(time
 (prn
  (apply +
        (for [s naturals :when (not (squares s))]
          (apply + (take 100 (ec/digits (isqrt-sub s 110))))))))

; 40886N
;"Elapsed time: 19.207494 msecs"

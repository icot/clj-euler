(ns p78
  (:require [clojure.tools.trace :as tr]
            [clojure.spec.alpha :as s]))

(defn pentagonal-number [^long n] (quot (- (* 3 n n) n) 2))

(def pent-number (memoize pentagonal-number))

(defn pentagon [^long n] (quot (* n (dec (* 3 n))) 2))
(def sgn (cycle '(1 -1)))
(def integers (iterate inc 1))

(def q
  (memoize
   (fn [k] (cond
            (zero? k) 1
            (neg? k) 0
            (= 1 k) 1
            :else (let [sign (cycle '(1 -1))  ; Damn cycle was in the wrong order!!!!
                        pair (fn [k'] (+ (q (- k (pent-number k')))
                                        (q (- k (pent-number (- k'))))))]
                    (mod
                  (reduce +
                    (take-while (comp not zero?)
                      (map #(* (pair %2) %1) sign integers)))
                  1000000))))))


;(tr/trace-vars p78/q)

(time (prn (first (filter #(zero? (q %)) integers))))

; After hitting my head with the wall for a while surrendered and looked for a
; clojure implementation to verify approach.
;
; After finding: https://github.com/rm-hull/project-euler/blob/master/src/euler078.clj
; found the bug in my code was the inverted ordered in the sign function.
;
; I refactored my original code to use a pair funcition like the linked reference, originally
; I was ussing the expanded expression and a cycle of four elements '[1 1 -1 -1]

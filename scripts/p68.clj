(ns p68
  (:require [clojure.math.combinatorics :as combo]))

(def p-values (range 1 11)) ; Brute Force: 3628800 permutations

; By putting the bigger values in the outside ring to maximize
; the segments, we get that the segment value hast to be
; (2 * (sum 1:5)) + sum(6:10) = 70 -> with 5 segments, each one
; hast to add to 14.

(def cs  (combo/combinations p-values 3))

(prn (filter #(= (reduce + %) 14) cs))

; ((1 3 10) (1 4 9) (1 5 8) (1 6 7) (2 3 9) (2 4 8) (2 5 7) (3 4 7) (3 5 6))

; Now it's a matter of combine permutations fo these tripplets

; We have to start with 6 and there's only one combination which leaves
; d > 5 in the outer rim : 6 5 3,
;
; Once this one is set, 10 3 1 and 7 2 5 fall into place, which eliminate
; some others options and we get:

; 6531031914842725

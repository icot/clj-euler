(ns p49
  (:require [euler.core :as ec])
  (:require [euler.primes :as ep])
  (:require [clojure.math.combinatorics :as combo]))

; Find primes between 1000 and 9999 such that at least two of its cipher permutations
; are also prime
;
(def cs (filter #(> % 1000) (ep/sieve 10000)))

(def ph (into (sorted-map) (zipmap cs (repeat 1))))

(defn digit-permutations [n]
    (map ec/seq-to-digits (combo/permutations (ec/digits n))))

(defn is-rotation? [a b]
  (= (into (sorted-set) (ec/digits a))
     (into (sorted-set) (ec/digits b))))

(def candidates
  (for [c cs :when (>= (reduce + (map #(get ph % 0) (digit-permutations c))) 3)] c))

(def fcs (filter #(< % (- (last candidates) (* 2 3330))) candidates))

(def fcs1 (filter #(and (ep/prime? (+ % 3330)) (ep/prime? (+ % 6660))) fcs))

(def fcs2 (filter #(is-rotation? % (+ % 3330)) fcs1))

(newline)
(prn (count fcs2))
(prn fcs2)

; 296962999629

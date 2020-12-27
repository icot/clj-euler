(ns p43
  (:require [clojure.math.combinatorics :as combo])
  (:require [clojure.set :as cs])
  (:require [euler.core :as ec])
  )

(def candidates (map ec/seq-to-digits (combo/permutations '(0 1 2 3 4 5 6 7 8 9))))

(defn p-test [n]
  (let [[d1 d2 d3 d4 d5 d6 d7 d8 d9 d10] (ec/digits n)
        d234 (ec/seq-to-digits (list d2 d3 d4))
        d345 (ec/seq-to-digits (list d3 d4 d5))
        d456 (ec/seq-to-digits (list d4 d5 d6))
        d567 (ec/seq-to-digits (list d5 d6 d7))
        d678 (ec/seq-to-digits (list d6 d7 d8))
        d789 (ec/seq-to-digits (list d7 d8 d9))
        d891 (ec/seq-to-digits (list d8 d9 d10))]
    (and
     (zero? (mod d891 17))
     (zero? (mod d789 13))
     (zero? (mod d678 11))
     (zero? (mod d567 7))
     (zero? (mod d456 5))
     (zero? (mod d345 3))
     (zero? (mod d234 2)))))

(defn unique-ciphers [n]
  (= (count (set (ec/digits n))) (count (ec/digits n))))

(defn filter-by-digits [ls n]
  (let [[d1 d2 d3] (ec/digits n)]
    (filter #(let [[c1 c2 c3] (ec/digits %)] (and (= c2 d1) (= c3 d2))) ls)))

(def d891 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 17) (range 200)))))
(def d789 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 13) (range 200)))))
(def d678 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 11) (range 200)))))
(def d567 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 7) (range 200)))))
(def d456 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 5) (range 200)))))
(def d345 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 3) (range 200)))))
(def d234 (filter unique-ciphers (filter #(and (> % 100) (< % 1000)) (map * (repeat 2) (range 200)))))

(def pool (list d891 d789 d678 d567 d456 d345 d234))

(newline)
;(prn d891)
;(prn d789)
;(prn (filter-by-digits d789 (first d891)))


; Brute Force solution

(prn (p-test 1406357289))

(def sol (for [c candidates :when (and (> c 999999999) (p-test c))] c))

(prn "Computing...")
(time (prn sol))
(prn (reduce + sol))

;"Computing..."
;(1406357289 1430952867 1460357289 4106357289 4130952867 4160357289)
;"Elapsed time: 42980.531829 msecs"
;16695334890

; TODO
; Method Outline
; - For each item in pool[n] -> filter -by digits [pool n-1], iterate with (rest pool) until pool is empty
; - For d0, missing cipher to make pandigital, if possible


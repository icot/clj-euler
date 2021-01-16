(ns p73
  (:require [euler.core :as ec]
            [euler.primes :as ep]))

(def limit 1000000)

(def fs (for [d (range 2 limit) n (range 1 d)] (/ n d)))

(defn valid? [q] (and (> q (/ 3)) (< q (/ 2))))

;; Brute Force loop
;; "Count 7295372 "
;; "Elapsed time: 176685.536141 msecs"

;(time
; (loop [n 1 d 2 acc #{}]
;  (let [q (/ n d)
;        next-acc (if (valid? q) (conj acc q) acc)]
;    (cond
;      (> d limit) (prn (format "Count %d " (count acc)))
;      (= n d) (recur 1 (inc d) next-acc)
;      :else (recur (inc n) d next-acc)))))

;;; As sum(phi(n) over d

; P72: Count number of reduced proper fractions with d <= 1e6

(def ds (range 2 (inc limit)))
(def primes (ep/sieve (inc (int (Math/sqrt limit)))))
(time (prn (reduce + (map #(ep/phi % primes) ds))))

;303963553387N
;303963552391  < - correct value ??? BUG: Bug in euler.primes/phi ?
;"Elapsed time: 11650.285708 msecs"

(ns p46
  (:require [euler.primes :as ep]))

(def ps (ep/sieve 20000))
(def ph (into (sorted-map) (zipmap ps (repeat true))))

(defn p-test [n ps]
  (let [pss (filter #(< % n) ps)
        ss (map #(* 2 % %) (range 1 n))]
    (empty? (for [p pss s ss :when (= n (+ p s))] (+ p s)))))

(def odd-gen (iterate (comp inc inc) 1))

(def composites (into [] (filter #(= false (ph % false)) (take 10000 odd-gen))))

(newline)

(def ss (take 2 (filter #(p-test % ps) composites)))

(time (prn ss))

; TODO: Solution in ~5.5s

(ns p50
  (:require [euler.primes :as ep]))

(def m-limit 1000000)

(def ph (into (sorted-map) (zipmap (ep/sieve m-limit) (repeat true))))

(def primes (ep/sieve m-limit))
(def prime-sums (filter #(< % m-limit) (reductions + primes)))

(def ps (reverse (map vector prime-sums (iterate inc 1 ))))

(newline)

(time
 (loop [psums ps limit m-limit]
  (if (seq psums)
    (let [ps1 (first (first psums))
          npsums (reverse (rest psums))
          sol (first (for [ps npsums :when (get ph (- ps1 (first ps)) false)] (- ps1 (first ps))))]
      (do
        (prn ps1 sol)
        (if (and (get ph sol false) (< sol limit))
          (prn "Solution:" sol ps1 (last (first psums)))
          (recur (rest psums) limit)
          )))
    (prn "EOL")
    )))

(ns p37)

; Python2: 748317 @ 39.40 sec
;

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def primes (euler.primes/sieve 1000000))
(def ph (into (sorted-map) (zipmap primes (repeat true))))

(defn is-prime? [n ph] (ph n false))

(defn solution? [n]
  (every? #(is-prime? % ph) (concat
                     (euler.core/truncations-left-to-right n)
                     (euler.core/truncations-right-to-left n))))

(def sps (for [p primes :when (and (> p 10) (solution? p))] p))

(time
 (do
   (prn (doall sps))
   (prn (count sps))
   (prn (reduce + sps))
   ))

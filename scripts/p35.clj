#!/usr/bin/env clj

(ns p35)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(time
 (def primes (euler.primes/sieve 1000000))
 )

(def ph (into (sorted-map) (zipmap primes true)))

; Bottleneck lies here
;(defn is-prime? [n primes] (not= (some #{n} primes) nil))

(defn is-prime? [n ph] (ph n false))

(time
 (def rps (for [p primes :when (every? #(is-prime? % ph) (euler.core/rotations p))] p))
 )

;❯ ./p35.clj
;Picked up _JAVA_OPTIONS: -Dawt.useSystemAAFontSettings=gasp
;"Elapsed time: 14.446251 msecs"
;"Elapsed time: 0.109768 msecs"
;55
;"Elapsed time: 555537.387483 msecs"

(time
 (prn (count rps)))

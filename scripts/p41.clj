(ns p41
  (:require [clojure.math.combinatorics :as combo])
  (:require [euler.core :as ec])
  (:require [euler.primes :as ep]))

(def primes (ep/sieve (inc 999999999)))

(def pp (filter ec/n-pandigital? primes))

(time
 (prn (last pp)))

;; 7652413
;; Elapsed time: 177.738s
;;
;; Possible improvements:
;;   * Generate n-pandigital numbers with combinations -> Filter even -> Test for primality
;;   * Start by n=9 and descend

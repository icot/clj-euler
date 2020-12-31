(ns p56
  (:require [euler.core :as ec]))

(defn mypow
  ([a b] (mypow a b 1))
  ([a b acc]
   (if (zero? b)
    acc
    (recur a (dec b) (*' a acc)))))


(defn digital-sum [n] (reduce + (ec/digits n)))

(time
 (prn
    (loop [i 1 m 0]
    (let [a (quot i 100)
            b (mod i 100)
            ds (digital-sum (mypow a b))]
        (if (= a 101)
        m
        (if (> ds m)
            (recur (inc i) ds)
            (recur (inc i) m)))))))

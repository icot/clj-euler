(ns p57
  (:require [euler.core :as ec])
  )

(defn f [k]
  (let [ds (cons 1 (take k (repeat 2)))]
    (do
      (reduce #(+ %2 (/ %1)) (reverse ds))))
      )

(defn process [f]
  (if (> (count (ec/digits (numerator f))) (count (ec/digits (denominator f)))) 1 0))

(time (prn (reduce + (map #(process (f %)) (range 1 1000)))))
; Elapsed 12s

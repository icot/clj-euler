(ns p52
  (:require [euler.core :as ec]))

(defn p-test [n]
  (let [ps (map * (repeat n) (range 2 7))
        ds (into (sorted-set) (ec/digits n))]
    (= 5 (count (filter #{ds} (map #(into (sorted-set) (ec/digits %)) ps))))))

(time
 (loop [n 1]
  (if (p-test n)
    (prn "Solution " n)
    (recur (inc n)))))

(ns p53
  (:require [euler.core :as ec]))

(time
 (loop [n 1 r 0 acc 0]
   (let [cs (ec/combinations n r)
         increment (if (> cs 1e6) 1 0)]
     (do
;       (prn n r cs)
       (cond
         (> n 100) (prn acc)
         (= n r) (recur (inc n) 0 (+ acc increment))
         :else (recur n (inc r) (+ acc increment)))))))

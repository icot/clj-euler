(ns p65
  (:require [euler.core :as ec]))


(defn e->cf [k]
  "Continuous fraction of e in list mode to the k-element"
  (concat (list 2 1 2) (interleave (repeat 1)
                                   (repeat 1)
                                   (map #(* % 2) (range 2 (inc k))))))

(defn unitary-numerators [ds k]
  "Compute approximation for the degenerate type where Ni = 1"
  (reduce #(+ %2 (/ %1)) (reverse (take k ds))))

(prn
 (reduce + (ec/digits (numerator (unitary-numerators (e 100) 100)))))


(ns p89
  (:require [clojure.string :as str]))

;; load input
(def raw-numbers (str/split-lines (slurp "scripts/resources/p089_roman.txt")))

(def L (reduce + (map count raw-numbers)))

(def VS {\M 1000
         \D 500
         \C 100
         \L 50
         \X 10
         \V 5
         \I 1})

(defn roman-to-int [s]
  (reduce + 0 (for [c s] (get VS c))))

(defn int-to-min-roman [N]
  (loop values VS n N r '()))
 






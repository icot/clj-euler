(ns p89
  (:require [clojure.string :as str]))

;; load input
(def raw-numbers (str/split-lines (slurp "/home/spike/workspace/clojure/euler/scripts/resources/roman.txt")))

(def L1 (reduce + (map count raw-numbers)))

;; TODO : Use threading macro
(def f1 (for [n raw-numbers] (str/replace n #"IV" "c")))
(def f2 (for [n f1] (str/replace n #"IX" "n")))
(def f3 (for [n f2] (str/replace n #"XL" "q")))
(def f4 (for [n f3] (str/replace n #"XC" "N")))
(def f5 (for [n f4] (str/replace n #"CD" "Q")))
(def f6 (for [n f5] (str/replace n #"CM" "k")))

(def VS {\M 1000
         \k 900
         \D 500
         \Q 400
         \C 100
         \N 90
         \L 50
         \q 40
         \X 10
         \n 9
         \V 5
         \c 4
         \I 1})


(def Numerals (reverse (list 
                        '(1 \I)
                        '(4 "IV")
                        '(5 \V)
                        '(9 "IX")
                        '(10 \X)
                        '(40 "XL")
                        '(50 \L)
                        '(90 "XC")
                        '(100 \C)
                        '(400 "CD")
                        '(500 \D)
                        '(900 "CM")
                        '(1000 "M"))))

(defn roman-to-int [s]
  (reduce + 0 (for [c s] (get VS c))))

(defn int-to-min-roman [N]
  (loop [numerals Numerals n N r '()]
    (do
;      (println N numerals n r)
      (if (or (<= n 0) (empty? numerals)) (str/join "" (reverse r))
          (let [pair (first numerals)]
            (if (>= n (first pair))
               (recur numerals (- n (first pair)) (conj r (last pair)))
               (recur (rest numerals) n r)))))))
               
(defn compute [roman-number]
  (-> roman-number
      roman-to-int
      int-to-min-roman
      count))

(def L2 (reduce + (map compute f6)))

;; 743
(println L1 L2 (- L1 L2))
    




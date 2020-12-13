(ns p40)

(def i '(1 10 100 1000 10000 100000 1000000))

(def s (clojure.string/join "" (flatten (map str (take 200000 (iterate inc 1))))))

(reduce * (map #(Character/digit % 10) (map nth (repeat s) (map dec i))))

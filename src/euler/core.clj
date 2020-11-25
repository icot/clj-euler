(ns euler.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn prime? [n] false)

(defn factors [n] nil)


(defn phi [n] nil)

(defn phi2 [n] nil)

(defn fib-seq
  "Laxy fibonacci sequence generator"
  ([]
     (fib-seq 0 1))
  ([a b]
     (lazy-seq
      (cons b (fib-seq b (+ a b))))))

(defn sum-digits [n] nil)


(defn triangle_gen [] nil)

(defn rotate [n] nil)
(defn rotations [n] nil)

(defn truncate_left [n] nil)
(defn truncate_right [n] nil)


(defn triangular? [n] nil)
(defn pentagonal? [n] nil)
(defn hexagonal? [n] nil)


(defn num-divisors [n] nil)
(defn divisors [n] nil)

(defn palindrome [n] nil)

(defn reverse-chiffres [n] nil)

(defn factorial [n] nil)

(defn concatenate-nums [a, b] nil)

(ns euler.core)

(defn fib-seq
  "Laxy fibonacci sequence generator"
  ([]
     (fib-seq 0 1))
  ([a b]
     (lazy-seq
      (cons b (fib-seq b (+' a b))))))

(defn sum-digits [n] nil)


(defn triangle_gen [] nil)

(defn rotate [n] nil)
(defn rotations [n] nil)

(defn truncate_left [n] nil)
(defn truncate_right [n] nil)


(defn triangular? [n] nil)
(defn pentagonal? [n] nil)
(defn hexagonal? [n] nil)


(defn palindrome?
  "Returns true if argument is a palindromic integer"
  [n]
  (let [ciphers (map identity (str n))
        reverse-ciphers (reverse ciphers)]
    (= ciphers reverse-ciphers))
  )

(defn reverse-chiffres [n] nil)

(defn factorial "Returns factorial of argument n"
  [n]
  (reduce *' (range 1 (inc n)))
  )
  
(defn concatenate-nums [a, b] nil)




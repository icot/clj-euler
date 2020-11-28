(ns euler.core)

(defn fib-seq "Laxy fibonacci sequence generator"
  ([]
     (fib-seq 0 1))
  ([a b]
     (lazy-seq
      (cons b (fib-seq b (+' a b))))))

(defn count-hash "Return a hash-map of {item => occurrences} on a supplied list"
  ([l] (count-hash {} l))
  ([acc l]
   (if (empty? l) acc
       (let [[head & tail] l]
         (if (get acc head)
           (recur (merge acc {head (inc (get acc head))}) tail)
           (recur (conj {head 1} acc) tail)
           )
         )
   )))

(defn sum-digits [n] nil)

(defn triangle-gen
  ([] (triangle-gen 1 0))
  ([n m] (lazy-seq
        (cons (+ n m) (triangle-gen (inc n) (+ n m))))))

(defn find-first
  "Find first element in collection coll satisfying filter f"
  [f coll]
  (first (drop-while (complement f) coll)))

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

(defn factorial "Returns factorial of argument n"
  [n]
  (reduce *' (range 1 (inc n)))
  )
  
(defn concatenate-nums [a, b] nil)




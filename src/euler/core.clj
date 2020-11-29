(ns euler.core)

(defn sum [l] "summatory" (reduce +' l))
(defn prod[l] "reduce list by product" (reduce *' l))

(defn tails [coll] (take-while seq (iterate rest coll)))
(defn heads [coll] (reductions conj [] coll))

(defn rotations [a-seq]
  (map concat (tails a-seq) (heads a-seq)))

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
  

; https://codereview.stackexchange.com/questions/135737/generate-all-permutations-in-clojure
; Slowish, and not lexicographic order
(defn permutations [a-set]
  (if (empty? a-set)
    (list ())
    (mapcat
     (fn [[x & xs]] (map #(cons x %) (permutations xs)))
     (rotations a-set))))

;(defn concatenate-nums [a, b] nil)
;(defn rotate [n] nil)
;(defn truncate_left [n] nil)
;(defn truncate_right [n] nil)
;
; https://en.wikipedia.org/wiki/Pentagonal_number#Tests_for_pentagonal_numbers
(defn pentagonal? [n]
  (let [p (/ (+ (Math/sqrt (+ (* n 24) 1)) 1) 6)]
    (zero? (- p (int p)))))

; https://en.wikipedia.org/wiki/Hexagonal_number
;
; Every hexagonal number is a triangular number, but only every other triangular number
; (the 1st, 3rd, 5th, 7th, etc.) is a hexagonal number.

(defn  hexagonal? [n]
  (let [p (/ (+ (Math/sqrt (+ (* n 8) 1)) 1) 4)]
    (zero? (- p (int p)))))


 (defn fib-seq
  "Returns a lazy sequence of Fibonacci numbers"
  ([]
     (fib-seq 0 1))
  ([a b]
     (lazy-seq
      (cons b (fib-seq b (+ a b))))))

(reduce + (filter even? (filter (fn [n] (< n 4000000)) (take 35 (fib-seq)))))
4613732

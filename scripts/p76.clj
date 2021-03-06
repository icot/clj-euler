(ns p76)

; How many different ways can one hundred be written as a sum of at least two positive integers?
;
; p_k(n) = p_k(n-k) + p_{k-1}(n-1)
; p(n) = sum_k(0,n, p_k(n))

(defn p_k [n k]
  "Restricted partitions of n taken in k ways"
  (cond
    (and (zero? n) (zero? k)) 1
    (or (<= n 0) (<= k 0)) 0
    :else (+ (p_k (- n k) k)
             (p_k (dec n) (dec k)))))

(defn integer-partition-count [n]
  "Total number of integer partitions"
  (let [p_k' (fn [n k]
               (cond
                 (and (zero? n) (zero? k)) 1
                 (or (<= n 0) (<= k 0)) 0
                 :else (+
                        (p_k' (dec n) (dec k))
                        (p_k' (- n k) k)
                        )))
        f' (memoize p_k')
        f (memoize p_k)]
    (reduce + (for [i (range (inc n))] (f' n i)))))

(newline)

(time
 (let [p (integer-partition-count 100)]
   (prn (format "Total %d" p))))

(time
 (let [p (integer-partition-count 100)]
   (prn (format "Total %d" p))))

; The actual solution to the problem needs to remove one solution (n)

; TODO: Recursive solution runs in 20s, maybe convert to a loop for better performance?

; "Total 190569292"
; "Elapsed time: 37120.021215 msecs"

#lang racket

; raco pkg install memoize
(require memoize)
(require loop)

(define/memo (p_k n k)
;  "Restricted partitions of n taken in k ways"
  (cond
    ((and (zero? n) (zero? k)) 1)
    ((or (<= n 0) (<= k 0)) 0)
    (else (+ (p_k (- n k) k)
             (p_k (sub1 n) (sub1 k))))))

(define (integer-partition-count n)
;  "Total number of integer partitions"
  (foldl + 0 (map (lambda (k) (p_k n k)) (range (add1 n)))))

(newline)

(define n 1)

(time
 (loop (not (= (remainder (integer-partition-count n)) 10))
   (set! n (add1 n)))
 (display n))

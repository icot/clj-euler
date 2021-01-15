#lang racket

(define (numdigits n) (+ 1 (exact-floor (/ (log n) (log 10)))))

(define (counter power)
  (let* ([powers (map (lambda (x) (expt x power)) (range 1 10))]
         [cs (filter (lambda (x) (= (numdigits x) power)) powers)])
    (length cs)))

(define brute-forced (map counter (range 1 30)))

brute-forced

(foldl + 0 brute-forced)

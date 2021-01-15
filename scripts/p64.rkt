#lang racket

(define (r->cf r)
  (define (cfraq r acc)
    (let* ([a0 (exact-floor r)]
           [ai (- r a0)])
      (cond
        ((zero? ai) (append acc (list a0)))
        ((and (not (empty? acc)) (= (* 2 (car acc)) a0)) (append acc (list a0)))
        (else (cfraq (/ ai) (append acc (list a0)))))))
  (cfraq r '()))


; https://en.wikipedia.org/wiki/Periodic_continued_fraction#cite_note-7
(define (sqrt->cf n)
  (define (cfraq S m d a acc)
    (let* ([a0 (car acc)]
           [next-m (- (* a d) m)]
           [next-d (/ (- S (* next-m next-m)) d)]
           [next-a (exact-floor (/ (+ (sqrt S) next-m) next-d))])
      (cond
        ((= (* a0 2) next-a) (append acc (list next-a)))
        (else (cfraq S next-m next-d next-a (append acc (list next-a)))))))
  (let* ([sqrt-n (sqrt n)]
         [a0 (exact-floor sqrt-n)])
    (if (integer? sqrt-n)
        (list sqrt-n)
        (cfraq n 0 1 a0 (list a0)))))

(define numbers (range 2 10001))
(define seqs (map sqrt->cf numbers))
;(display seqs)

(length (filter odd? (map (lambda (x) (sub1 (length x))) (filter (lambda (x) (> (length x) 1)) seqs))))

; 1322


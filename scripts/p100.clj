(ns p100)

;; Solve equity to generate Pell's equation

;; From solution, iterative generator:

(time
 (loop [b 85 n 120]
  (if (< n (bigint 1e12))
    (let [b' (+ (* 3 b) (* 2 n) (- 2))
          n' (+ (* 4 b) (* 3 n) (- 3))]
      (recur b' n'))
    (prn b n))))

;; Writing a comment


; a
;
;
;

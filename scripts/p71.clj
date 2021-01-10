(ns p71)

;(time
; (prn "Solution"
;  (numerator
;   (last
;    (filter #(< % (/ 3 7))
;            (seq (into (sorted-set) (for [d (range 1 10001) n (range 1 d)] (/ n d)))))))))

(def limit 1000000)
(def target (/ 3 7))

;(loop [d 1 n 0 min-diff target mq 0]
;  (if (<= d limit)
;    (let [new-diff-raw (- target (/ n d))
;          new-diff (if (and (pos? new-diff-raw) (< new-diff-raw min-diff)) new-diff-raw min-diff)
;          new-mq (if (and (pos? new-diff) (< new-diff min-diff)) (/ n d) mq)]
;      (do
;;        (prn "q" (/ n d) "min-diff" min-diff "new-diff" new-diff "mq" mq  "new-mq" new-mq)
;        (if (= (inc n) d)
;          (do
;            (if (zero? (mod d 1000)) (prn "d" d))
;            (recur (inc d) 0 new-diff new-mq))
;          (recur d (inc n) new-diff new-mq)))
;      )
;    (prn "Result" mq)))


; Generating all co-primes pairs
; https://en.wikipedia.org/wiki/Coprime_integers
;
; Two Ternary trees with with the following branches, m > n, and seeds [2, 1] and [3, 1]
;  - (2m -n,m)
;  - (2m +n, m)
;  - (m + 2n, n)

(defn branches [[m n]]
  (let [b1 (vector (- (* 2 m) n) m)
        b2 (vector (+ (* 2 m) n) m)
        b3 (vector (+ (* 2 n) m) n)]
    (vector b1 b2 b3)))

(defn f
  ([] (concat (branches [2 1]) (branches [3 1])))
  ([s] (apply concat (map branches s))))

(def i1 (f '[[2 1] [3 1]]))
(def i2 (f i1))

(def coprime-pairs (iterate f '[[2 1] [3 1]]))

;(prn (first coprime-pairs))
;(prn (second coprime-pairs))
;(prn (nth coprime-pairs 4))

(defn distance [v]
  (let [dist (- (/ 3 7) (/ (second v) (first v)))]
    (if (pos? dist) dist 1)))

(defn process [coprimes best min-distance limit]
  (let [pairs (first coprimes)
        maxd (apply max (map first pairs))
        x-pairs (zipmap pairs (map distance pairs))
        x-candidates (filter #(< (last %) min-distance) (seq x-pairs))
        elem (first (sort-by last x-candidates))
        new-best (first elem)
        new-min-distance (last elem)]
    (if (< maxd limit)
      (recur (rest coprimes) new-best new-min-distance limit)
      (prn "best: " best (double (/ (last best) (first best))) "min-distance: " min-distance)
      )))

; Starting from p / q < a / b
(time (loop [q limit a 3 b 7 r 0 s 1]
  (if (> q 2)
    (let [p (long (Math/floor (/ (dec (* a q)) b)))]
        (if (> (* p s) (* r q))
          (recur (dec q) a b p q)
          (recur (dec q) a b r s)))
    (prn "Solution" r s))))

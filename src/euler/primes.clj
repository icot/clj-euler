(ns euler.primes)

(use 'euler.core)

; https://clojureverse.org/t/eratosthenes-party-time-a-k-a-feedback-wanted-on-this-implementation-of-eratosthenes-sieve/3801/16
(use 'clojure.set)

(defn sieve [n]
  (if (< n 2)
    ()
    (let [sqrt-n (Math/sqrt n)]
      (loop [primes (set (range 3 (inc n) 2))
             p 3]
        (if-not (< p sqrt-n)
          (concat '(2) (sort primes))
          (recur (difference primes (set (range (* p p) n (+ p p)))) (+ p 2)))))))

(defn sieve [^long n]
  (let [primes (boolean-array (inc n) true)
        sqrt-n (int (Math/ceil (Math/sqrt n)))]
    (if (< n 2)
      '()
      (loop [p 3]
        (if (< sqrt-n p)
          (concat '(2)
                  (filter #(aget primes %)
                          (range 3 (inc n) 2)))
          (do
            (when (aget primes p)
              (loop [i (* p p)]
                (if (<= i n)
                  (do
                    (aset primes i false)
                    (recur (+ i p p))))))
            (recur  (+ p 2))))))))

(defn proper-divisors
  "Returns a list of the proper (< n) integer divisors of n"
  [n]
  (letfn [(divisors-helper [acc n i max]
            (if (> i max) acc
                (if (zero? (mod n i))
                  (recur (cons i acc) n (inc i) max)
                  (recur acc n (inc i) max))))]
      (divisors-helper '() n 1 (Math/round (Math/floor (/ n 2))))))

(defn divisors
  "Returns a list of all integer divisors of n"
  [n]
  (cons n (proper-divisors n)))

; euler.core=> (time (factorize-naive 112346781234901234))
; "Elapsed time: 28638.889987 msecs"
; (8024770088207231 7 2 1)
(defn factorize-naive
  "Returns a list of the integer factors of argument n"
  [n]
  (let [ stop (Math/round (Math/sqrt n))
         factor-helper (fn [remanent factor factors]
          (let [
                next-remanent (quot remanent factor)
                stop? (< stop factor)
                branch? (zero? (mod remanent factor))
                next-factor (if (= factor 2) 3 (+ factor 2))
                ]
              (if stop?
                (if (= remanent 1) factors (cons remanent factors))
                (if branch?
                  (recur next-remanent factor (cons factor factors))
                  (recur remanent next-factor factors)
                  )
                )
              )
            )
          ]
    (factor-helper n 2 '(1)))
  )

; euler.core=> (time (factorize-sieve 112346781234901234))
; "Elapsed time: 12245.08063 msecs"
; (8024770088207231 7 2 1)
;
(defn factorize-sieve
  "Returns a list of the integer factors of argument n"
  ([n] (factorize-sieve n (sieve (Math/round (Math/sqrt n)))))
  ([n primes]
   (let [factor-helper (fn [remanent factor factors primes]
                        (do
                  ;;      (println remanent factor factors)
            (let [
                  next-remanent (quot remanent factor)
                  branch? (zero? (mod remanent factor))
                  [next-factor & remaining-primes] primes
                  ]
                (if (or (empty? primes) (= remanent 1))
                  (if (= remanent 1) factors (cons remanent factors))
                  (if branch?
                    (recur next-remanent factor (cons factor factors) primes)
                    (recur remanent next-factor factors remaining-primes)
                    )
                  )
                )
              )
                          )
         ]
     (factor-helper n 2 '(1) primes))))

; Theorem 273: An Introduction to the Theory un Numbers (G.H Hardy and E.M Wright)
(defn num-divisors "Returns number of divisors for n"
  [n]
  (let [f (factorize-sieve n)]
    (if (= 2 (count f))
      2
      (/ (reduce * (map inc (vals (count-hash f)))) 2))))

; https://stackoverflow.com/questions/960980/fast-prime-number-generation-in-clojure


; Ported from https://web.archive.org/web/20150710134640/http://diditwith.net/2009/01/20/YAPESProblemSevenPart2.aspx
(defn gen-primes "Generates an infinite, lazy sequence of prime numbers"
  []
  (letfn [(reinsert [table x prime]
             (update-in table [(+ prime x)] conj prime))
          (primes-step [table d]
             (if-let [factors (get table d)]
               (recur (reduce #(reinsert %1 d %2) (dissoc table d) factors)
                      (inc d))
               (lazy-seq (cons d (primes-step (assoc table (* d d) (list d))
                                              (inc d))))))]
    (primes-step {} 2)))

;def eratosthenes(n):
;    candidates = [item for item in range(3, n, 2)]
;    L = len(candidates)
;    for cpos, c in enumerate(candidates):
;        if c:
;            for pos in range(cpos+c, L, c):
;                candidates[pos] = 0
;    candidates.insert(0,2)
;    return [item for item in candidates if item > 0]

(defn sieve-eratosthenes
  "Prime sieve for prime numbers sequence generation up to n"
  ([n]
    (let [candidates (range 3 n 2)
          c (count candidates)]
      (sieve-eratosthenes candidates 0 (dec c))
      ))
  ([candidates i c]
   (do
   (if (and (<= c i) (zero? (nth candidates i)))
     (recur candidates (inc i) c)
     (if (<= c i)
        (cons 2 (filter (complement zero?) candidates))
        (letfn [(candidate-filter [n]
                  (cond
                    (zero? (nth candidates i)) n
                    (= n (nth candidates i)) n
                    :else (if (zero? (mod n (nth candidates i))) 0 n))
                  )]
          (recur (map candidate-filter candidates) (inc i) c))
        ))
   ); do
   )

  )

(defn gcd [a b]
  (if (> a b)
    (if (zero? b)
      a
      (recur b (mod a b)))
    (recur b a)))

(defn phi
  ([n] (reduce + (for [k (range n) :when (= (gcd n k) 1)] 1)))
  ([n primes]
   (let [dfs (into (sorted-set) (filter #(> % 1)) (factorize-sieve n primes))]
     (*' n (reduce *' (map #(- 1 (/ 1 %)) (seq dfs)))))))

(defn prime? [n] (= (count (divisors n)) 2))

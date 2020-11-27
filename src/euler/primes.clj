(ns euler.primes)

(defn divisors
  "Returns a list of integer divisors of argument n"
  [n]
  (letfn [(divisors-helper [acc n i max]
            (if (> i max) acc
                (if (zero? (mod n i))
                  (divisors-helper (cons i acc) n (inc i) max)
                  (divisors-helper acc n (inc i) max))))]
    (divisors-helper '() n 1 (Math/round (Math/floor (/ n 2))))))

(defn num-divisors [n] (count (divisors n)))

(defn sieve [n] nil)

; euler.core=> (time (euler.primes/factorize 20000000001235))
; "Elapsed time: 409.103217 msecs"
; (2595717067 67 23 5 1)

(defn factorize
  "Returns a list of the integer factors of argument n"
  [n]
  (letfn [(factor-helper [remanent factor factors]
            (let [
                  next-remanent (quot remanent factor)
                  stop? (<= (Math/round (Math/sqrt n)) factor)
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

(defn phi [n] nil)

(defn phi2 [n] nil)

(defn prime? [n] false)

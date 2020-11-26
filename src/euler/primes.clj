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

(defn phi [n] nil)

(defn phi2 [n] nil)


(defn prime? [n] false)

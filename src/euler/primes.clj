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

(defn factorize
  "Returns a list of the integer factors of argument n"
  [n]
  (letfn [(factor-helper [remanent factor factors]
            (do
              (println remanent factor factors)
              (let [
                    next-remanent (quot remanent factor)
                    stop? (<= remanent factor)
                    branch? (zero? (mod remanent factor))
                    next-factor (if (= factor 2) 3 (+ factor 2))
                    ]
                  (if stop?
                    (cons factor factors)
                    (if branch?
                      (factor-helper next-remanent factor (cons factor factors))
                      (factor-helper remanent next-factor factors)
                      )
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

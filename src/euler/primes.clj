(ns euler.primes)

(defn bar
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn divisors
  "Return a list of integer divisors of argument n"
  [n]
  (letfn [(divisors-helper [acc n i max]
            (if (> i max) acc
                (if (zero? (mod n i))
                  (divisors-helper (cons i acc) n (inc i) max)
                  (divisors-helper acc n (inc i) max))))]
    (divisors-helper '() n 1 (Math/round (Math/floor (/ n 2))))))

(defn num-divisors [n] (count (divisors n)))

(defn sieve [n] nil)

(defn factor
  "Return a list of the integer factors of argument n"
  [n]
  (letfn [(factor-helper [acc f rem]
            (do
              (println acc f rem)
              (let [
                    next (quot rem f)
                    stop? (<= rem f)
                    branch? (zero? (mod rem f))
                    nf (if (= f 2) 3 (+ f 2))
                    ]
                  (if stop?
                    (cons f acc)
                    (if branch?
                      (factor-helper (cons f acc) f next)
                      (factor-helper acc nf rem)
                      )
                    )
                  )
                )
            )
          ]
    (factor-helper '(1) 2 n))
  )

(defn phi [n] nil)

(defn phi2 [n] nil)



(defn prime? [n] false)

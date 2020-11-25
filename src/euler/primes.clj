(ns euler.primes)

(defn bar
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn divisors
  "Return a list of integer divisors of argument n"
  [n]
  (letfn [(divisors-helper [acc n i max]
            (if (> n max) acc
                (if (= (mod n i) 0)
                  (divisors-helper (cons i acc) n (inc i) max)
                  (divisors-helper acc n (inc i) max))))]
    (divisors-helper nil n 1 (round (floor (/ n 2))))))


(defn num-divisors [n] (count (divisors n)))

(defn sieve [n] nil)

(defn factors
  "Return a list of the integer factors of argument n"
  [n]
  nil)



(defn phi [n] nil)

(defn phi2 [n] nil)



(defn prime? [n] false)

(ns euler)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def day-months '(31 28 31 30 31 30 31 31 30 31 30 31))
(def day-months-leap '(31 29 31 30 31 30 31 31 30 31 30 31))

;(println day-months)
;(println day-months-leap)

(defn leap-year [yyyy]
  (if (and (zero? (mod yyyy 100)) (zero? (mod yyyy 400)))
    false
    (if (and (not (zero? (mod yyyy 100)))
             (zero? (mod yyyy 4)))
      false
      true)
    )
  )

(defn get-first-mondays [first-mondays day years]
  (let [[year & remaining-years] years]
    (if (nil? year) (sort (flatten first-mondays))
        (let [ms (if (leap-year year) day-months day-months-leap)
              day-of-month (flatten (map range ms))
              days-of-year (count day-of-month)
              day-range (range day (+ day days-of-year))
              pairs (map vector day-of-month day-range)
              pairs-filtered (filter #(zero? (first %)) pairs)
              year-firsts (map #(last %) pairs-filtered)
              mondays (filter #(zero? (mod % 7)) year-firsts)]
        (do
;          (println year days-of-year pairs-filtered)
          (recur (cons mondays first-mondays) (+ day days-of-year) remaining-years)
        )
        )
      )
    )
  )


(def days (drop-while #(<= % 365) (get-first-mondays '() 1 (range 1900 2001))))
;(println days)
(println "Result" (count days))

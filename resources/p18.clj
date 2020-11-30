(ns p18
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def filename "./18.input")
;(def filename "./p067_triangle.txt")

(def data (map #(str/split % #" ") (str/split (slurp filename) #"\n")))

(def int-data (into [] (doall (map #(map (fn [s] (Integer/parseInt s)) %) (doall data)))))

(defn reduce-max-row
  ([row] (reduce-max-row row '[]))
  ([row new-row]
   (do
;     (println row new-row)
   (if (= (count row) 1) new-row
       (if (> (first row) (second row))
         (recur (rest row) (conj new-row (first row)))
         (recur (rest row) (conj new-row (second row)))
      )
    )
     )
   )
  )

(println int-data)

(defn main-loop [zig]
  (let [
        reduced-last (reduce-max-row (last zig))
        trimmed-zig (butlast zig)
        new-last (map + (last trimmed-zig) reduced-last)
        new-zig (reverse (cons new-last (reverse trimmed-zig)))
        ]
    (do
      (println "reduced-last" reduced-last)
      (println "trimmed-zig" trimmed-zig)
      (println "new-last" new-last)
      (println "new-zig" new-zig)
      (read-line)
    (if (= (count zig) 1) zig
        (recur new-zig)
        )
    )
      )
  )

(println (main-loop int-data))

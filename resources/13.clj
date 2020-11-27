(require '[clojure.string :as str])

(def s (slurp "./13.input"))

(println (take 10 (str (reduce +' (map #(BigInteger. %) (str/split s #"\n"))))))



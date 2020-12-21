(ns p99
  (:require [clojure.string :as str]))

(def filename "resources/p099_base_exp.txt")
(def data (map #(str/split % #",") (str/split-lines (slurp filename))))

(loop [l 1 m 0 ml 0 lines data]
  (let [line (seq (first lines))
        b (Integer/parseInt (first line))
        exp (Integer/parseInt (last line))
        line-value (* exp (Math/log b))]
    (if (= l (dec (count data)))
      (prn "max in line: " ml)
      (if (> line-value m)
        (do
            (prn "line: " l)
            (recur (inc l) line-value l (rest lines))
            )
        (recur (inc l) m ml (rest lines))
        )
      )
    )
  )

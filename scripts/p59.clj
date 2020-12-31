(ns p59
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as combo]))

; Path for Cider needs to be relative to the parent project folder
;
(def data (map #(Integer/parseInt %) (str/split (slurp "scripts/resources/p059_cipher.txt") #",")))

(defn vchar-ratio [cs]
  (let [C (count cs)
        c (count (filter #(and (>= % 32) (<= % 126)) cs))]
    (if (zero? C)
      0
      (/ c C))))

(defn count-spaces [cs]
  (count (filter #(= % 32) cs)))

(defn find-key
  ([cs] (find-key cs 0 0 0))
  ([cs k best-k best-score]
    (let [clear-text (map #(bit-xor % k) cs)
          score (vchar-ratio clear-text)]
      (if (= k 255)
        (char best-k)
        (if (> score best-score)
          (recur cs (inc k) k score)
          (recur cs (inc k) best-k best-score))))))

(defn apply-key [cs k]
  (let [ks (cycle (map int k))]
    (map bit-xor cs ks)))

(defn display [cs] (str/join "" (map char cs)))

(newline)

(def c1 (doall (take-nth 3 data)))
(def c2 (doall (take-nth 3 (rest data))))
(def c3 (doall (take-nth 3 (rest (rest data)))))

(def valid-ascii (concat (range 97 123)))
(def ks (combo/combinations valid-ascii 3))

(prn "Key space" (count ks))

(loop [mykeys ks]
  (let [k (first mykeys)
        cleartext (apply-key data k)
        cs (count-spaces cleartext)
        vr (vchar-ratio cleartext)]
    (if (nil? k)
      true
      (if (>= cs 60)
        (do
          (prn "k" (display k) (display (take 50 cleartext)))
          (recur (rest mykeys)))
        (recur (rest mykeys))))))

(prn "Done")

(ns p59
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as combo]))

; Path for Cider needs to be relative to the parent project folder
;
(def data (map #(Integer/parseInt %) (str/split (slurp "scripts/resources/p059_cipher.txt") #",")))

(defn vchar-ratio [cs]
  (let [c (count (filter #(and (>= % 32) (<= % 126)) cs))]
      (/ c 1455)))

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

(def valid-characters (concat (range 97 123)))
(def ks (for [a valid-characters b valid-characters c valid-characters] (list a b c))) ; Misses repetitions

(prn "Key space" (count ks))

(time
(loop [mykeys ks]
  (let [k (first mykeys)
        cleartext (apply-key data k)
        cs (count-spaces cleartext)
        vr (vchar-ratio cleartext)
        stop-cond? (.contains (display cleartext) " the ")]
    (if (nil? k)
      (prn "Done")
      (do
        (prn "k" (display k) (vchar-ratio cleartext) stop-cond? (display (take 30 cleartext)))
        (if stop-cond?
          (do
            (prn (display cleartext))
            (prn "Solution" (reduce + cleartext)))
          (recur (rest mykeys)))))))
)

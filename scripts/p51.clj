(ns p51
  (:require [clojure.string :refer [join]])
  (:require [clojure.math.combinatorics :refer [cartesian-product]])
  (:require [clj-async-profiler.core :as prof])
  (:require [euler.core :as ec])
  (:require [euler.primes :as ep]))

; TODO move this function to euler core
(defn group-map-keys-by-value [h]
  (let [g (group-by val h)
        vals (map #(map first %) (vals g))]
    (zipmap (keys g) vals)))

(defn selectors [l]
  (filter any? (rest (apply cartesian-product (repeat l [false true])))))

(defn compress [data selectors]
  (join "" (for [ d (map vector (ec/digits data) selectors) :when (last d)] (first d))))

(defn stest [data selectors k]
  (= 1 (count (set (compress data (map not selectors))))))

(prn "Generating primes")
;(time (def primes (into [] (as-> (ep/sieve 1000000) ps
;              (filter #(> % 100000) ps)))))
(time (def primes (into [] (as-> (ep/sieve 1000000) ps
              (filter #(> % 100000) ps)))))

(newline)

(prn "Generating families")
(time (def families (as-> (for [p primes]
                         (let [k (count (ec/digits p))]
                           (for [s (selectors k)]
                             (let [k1 (compress p s) k2 s]
                               (hash-map (list k k1 k2) (list p)))))) x
                (apply concat x)
                (apply merge-with concat x))))

; Profile requires
;   - echo 1 | sudo tee /proc/sys/kernel/perf_event_paranoid
;   - echo 0 | sudo tee /proc/sys/kernel/kptr_restrict

(prn "Generating families2")

(defn myconj
  ([x] (conj [] x))
  ([xs x] (conj xs x)))

(prof/profile (time (def families2
                      (apply merge-with myconj (mapcat (fn [p] (let [k (count (ec/digits p))]
                                       (for [s (selectors k)]
                                         (let [k1 (compress p s) k2 s]
                                           (hash-map (vector k k1 k2) p))))) primes)))))

(def a (first families2))
(def b (concat (first families2) (second families2)))

(time (prn (count primes)))
(time (prn (count families)))
(time (prn (count families2)))

;(prn families2)



;(prn "Looking for longest family")
;
;; TODO: Solved but tooooo slow. 248s for this loop? Python version runs in 15s
;;
;; - Removing sort from buf doesn't have visible effect
;; - Reducing the selectors can be notable (Removing selectors beginning with false -> 52s)
;; - Stopping the search when found first 8-family -> 14 seconds
;
;(time (loop [f families maxl 1]
;  (if (empty? f)
;    (prn "Done" maxl)
;    (let [item (first f)
;          k (first item)
;          v (last item)
;          k1 (second k)
;          selector (last k)
;          buf (for [x (into '[] (set v)) :when (stest x selector k1)] x)
;          new-maxl (if (> (count buf) maxl) (count buf) maxl)]
;      (do
;        (if (> new-maxl maxl)
;          (prn k1 selector (set v) buf))
;        (if (= 8 (count buf))
;          (do
;            (prn "Done" buf))
;;            (recur (rest f) new-maxl))
;          (recur (rest f) new-maxl)))))))

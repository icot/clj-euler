(ns p51
  (:require [clojure.math.combinatorics :refer [cartesian-product]])
  (:require [euler.primes :as ep])
  (:require [euler.core :as ec])
  )

; TODO move this function to euler core
(defn group-map-keys-by-value [h]
  (let [g (group-by val h)
        vals (map #(map first %) (vals g))]
    (zipmap (keys g) vals)))

;def compress(data, selectors):
;    l = (d for d, s in zip(data, selectors) if not s)
;    r = []
;    try:
;        while 1:
;            r.append(next(l))
;    except StopIteration:
;        return int(str(''.join(r)))
;

(defn compress [data selectors]
  (ec/seq-to-digits (for [ d (map vector (ec/digits data) selectors) :when (last d)] (first d))))

;def test(data, selectors):
;    l = (d for d, s in zip(data, selectors) if s)
;    r = []
;    try:
;        while 1:
;            r.append(next(l))
;    except StopIteration:
;        return len(set(r)) == 1

(defn stest [data selectors]
  (for [ d (map vector (ec/digits data) selectors) :when (last d)] d))

(defn selectors [l]
    (apply cartesian-product (repeat l [false true])))

;(def primes (filter #(> % 50000) (ep/sieve 100000)))
(def primes (filter #(> % 10) (ep/sieve 100)))
(def dp (zipmap primes (map #(count (str %)) primes)))
(def pdict (group-map-keys-by-value dp))

(newline)
; (prn (for [s (selectors 3)] (compress 123 s)))

;    families = {}
;    print("Generating prime families")
;    for k in list(pdict.keys()):
;        ss = selectors(k)
;        primes = pdict[k]
;        for selector in ss:
;            for prime in primes:
;                try:
;                    k1 = compress(str(prime), selector)
;                    k2 = ''.join(map(str, selector))
;                    families[(k,k1,k2)].append(prime)
;                except KeyError:
;                    families[(k,k1,k2)] = [prime]

(prn "Generating families")
(def families (as-> (for [p primes]
                         (let [k (count (ec/digits p))]
                           (for [s (selectors k)]
                             (let [k1 (compress p s) k2 s]
                               (hash-map (list k k1 k2) (list p)))))) x
                (apply concat x)
                (apply merge-with concat x)))

;    maxl = 0
;    octofam = []
;    for k, v in list(families.items()):
;        buf = list(set(v))
;        selector = list(map(int, k[-1]))
;        buf = [x for x in buf if test(str(x), selector)]
;        buf.sort()
;        l = len(buf)
;        if l > maxl:
;            print((k, buf))
;            maxl = l
;        if len(buf) == 8:
;            octofam.append(buf)
;    print((len(octofam), octofam))

(prn "Looking for longest family")

(loop [f families maxl 0 octofam '()]
  (if (empty? f)
    (prn "Done:" (map count octofam))
    (let [item (first f)
          k (first item)
          v (last item)
          selector (last k)
          buf (sort (for [x (into '() (set v)) :when (stest x selector)] x))
          new-maxl (if (> (count buf) maxl) (count buf) maxl)]
      (do
        (if (> new-maxl maxl)
          (prn k buf))
      (if (= 8 (count buf))
        (recur (rest f) new-maxl (concat octofam (list buf)))
        (recur (rest f) new-maxl octofam))))))

(prn (map compress (repeat 123) (selectors 3)))

(prn (compress 134 '(true true true false)))

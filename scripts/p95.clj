(ns p95)

(def MAX 1000000)

(defn proper-divisors
  "Returns a list of the proper (< n) integer divisors of n"
  [n]
  (letfn [(divisors-helper [acc n i max]
            (if (> i max) acc
                (if (zero? (mod n i))
                  (recur (conj acc i) n (inc i) max)
                  (recur acc n (inc i) max))))]
    (let [seeds (divisors-helper '[] n 1 (Math/round (Math/sqrt n)))]
      (concat seeds (for [s seeds :when (> s 1)] (/ n s))))))

(defn divsum [n] (reduce + (proper-divisors n)))

(def memoized-divsum (memoize divsum))

(time (def buf (into () (for [n (range (inc MAX))] (memoized-divsum n)))))

(defn rec-divsum [n]
  (loop [friends (sorted-map) k n]
    (let [ds (memoized-divsum k)]
      (cond
        (= ds k) nil ;; Found a perferct number 
        (or (> ds MAX)
            (and (find friends ds) (not (find friends n)))) nil
        (and (find friends n) (find friends ds)) friends
        :else (recur (assoc friends ds 1) ds)))))

;; Naive approach 100K -> 117s
(time (dotimes [n MAX]
        (let [rd (rec-divsum n)]
          (if rd
              (println n rd)))))




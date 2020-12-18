(ns p29)

(time (println (count (into #{} (for [a (range 2 (inc 100)) b (range 2 (inc 100))] (Math/pow a b))))))

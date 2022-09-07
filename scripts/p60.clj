(ns p60
  (:require [clojure.string :as str]))

(defn catnums [a b]
  (Integer/parseInt (str/join "" (concat (str a) (str b)))))

(prn (catnums 1234 3245))

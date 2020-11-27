(reduce + (map #(Character/digit %1 10) (str (euler.core/factorial 100))))

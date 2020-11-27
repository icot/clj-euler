(reduce + (filter #(< %1 2000000) (take 200000 (euler.primes/gen-primes))))

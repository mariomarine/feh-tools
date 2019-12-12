(require '[clojure.string :as str])
(defn getChance []
  (println "3 Star Chance (%): ")
  (def threes (read-line))
  (def threes (if (= threes "") "36" threes))
  (def threes (Integer. threes))
  (println "4 Star Chance (%): ")
  (def fours (read-line))
  (def fours (if (= fours "") "58" fours))
  (def fours (Integer. fours))
  (println "5 Star Chance (%): ")
  (def fives (read-line))
  (def fives (if (= fives "") "3" fives))
  (def fives (Integer. fives))
  (println "5 Star-Focus Chance (%): ")
  (def focus (read-line))
  (def focus (if (= focus "") "3" focus))
  (def focus (Integer. focus))
  (list threes fours fives focus)
)
(println "Focus colors (red red blue grey): ")
(def focus_color_wheel (read-line))
(def focus_color_wheel (if (= focus_color_wheel "") "red red blue grey" focus_color_wheel))
(def focus_color_wheel (str/split focus_color_wheel #" "))

;; Taken from https://stackoverflow.com/questions/14464011/idiomatic-clojure-for-picking-between-random-weighted-choices
;; Credit to Rich Hickey (solution) and dripnet (stackoverflow answer)
(defn wrand 
  "given a vector of slice sizes, returns the index of a slice given a
  random spin of a roulette wheel with compartments proportional to
  slices."
  [slices]
  (let [total (reduce + slices)
        r (rand total)]
    (loop [i 0 sum 0]
      (if (< r (+ (slices i) sum))
        i
        (recur (inc i) (+ (slices i) sum))))))

(defn getOrbStar [threes_chance fours_chance fives_chance focuses_chance] 
  (def pick (wrand [threes_chance fours_chance fives_chance focuses_chance]))
  (if (= pick 0) "three"
  (if (= pick 1) "four"
  (if (= pick 2) "five"
  "focus"))
  )
)

(def pool_numbers (hash-map "red" (hash-map "focus" 0 "five" 49 "four" 33 "three" 33)
                            "blue" (hash-map "focus" 0 "five" 35 "four" 32 "three" 30)
                            "green" (hash-map "focus" 0 "five" 31 "four" 23 "three" 20)
                            "grey" (hash-map "focus" 0 "five" 27 "four" 34 "three" 30)
                  )
)

(doseq [col focus_color_wheel] (def pool_numbers
                                 (assoc-in pool_numbers [col "focus"]
                                   (inc (get-in pool_numbers [col "focus"]))
                                 )
                               )
)



(defn getColor [orbStar]
  (def group (wrand [(get-in pool_numbers ["red" orbStar])
                     (get-in pool_numbers ["blue" orbStar])
                     (get-in pool_numbers ["green" orbStar])
                     (get-in pool_numbers ["grey" orbStar])]
             )
  )
  (if (= group 0) "Red"
             (if (= group 1) "Blue"
             (if (= group 2) "Green"
             (if (= group 3) "Grey")))
  )
)

(defn Summon [three_chance four_chance five_chance focus_chance]
  (def orbs [])
  (dotimes [i 5] (def orbs (conj orbs (hash-map "stars" (getOrbStar three_chance four_chance five_chance focus_chance)))))
  (def new_orbs [])
  (doseq [orb orbs] (def new_orbs (conj new_orbs (assoc orb "color" (getColor (get orb "stars"))))))
  (dotimes  [i 5] (println "Orb" i ":" (new_orbs i)))
)

(apply Summon (getChance))


(ns hero-helper.core
  (:gen-class)
  (:require [clojure.data.json :as json])
)
(require '[clojure.string :as string])

(defn transformSkills [skill]
  {:name (skill "name")
   :rarity (skill "rarity")}
)

(defn getRarity [row]
  (into [] (map #(Integer. (re-find #"\d" %)) (keys (select-keys row ["rarity1", "rarity2", "rarity3", "rarity4",  "rarity5"]))))
)

(defn transformData [entry]
  {:name (entry "name")
   :title (entry "title")
   :skills (into [] (map transformSkills (entry "skills")))
   :rarities (getRarity entry)
   :exclusive (contains? entry "limited")}
)

(defn hasSkill
  "Takes a hero and a skill name, and returns true/false whether that hero has that skill"
  [hero, skillName]
  (.contains (into [] (map #(get % :name) (get hero :skills))) skillName)
)

(defn searchForSkill
  "Takes a skill name and a seq of heroes, and returns those heroes who have the skill"
  [skillName, dataSet]
  (filter #(hasSkill % skillName) dataSet)
)

(defn printableHero [hero]
  (str (hero :name) ", " (hero :title) "\n"
       "Rarities: " (hero :rarities) "\t" "Exclusive: " (hero :exclusive) "\n"
       "Skills: " (string/join ", " (into [] (map #(str (get % :name) " (" (get % :rarity) "*)" ) (get hero :skills)))) "\n"
       "\n"
  )
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def data (json/read-str (slurp "hero-data.json")))
  (def newData (into [] (map transformData data)))
  (def skillToSearch "Distant Counter")
  (println (str "Searching for: " skillToSearch))
  (println)
  (println (string/join (map #(printableHero %) (searchForSkill skillToSearch newData))))
)

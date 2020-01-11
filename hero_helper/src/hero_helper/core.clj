(ns hero-helper.core
  (:gen-class) (:require [clojure.data.json :as json]))

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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def data (json/read-str (slurp "hero-data.json")))
  (def newData (into [] (map transformData data)))
  (println newData)
)

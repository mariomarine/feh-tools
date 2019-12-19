(ns hero-helper.core
  (:gen-class)
  (:import [org.jsoup Jsoup]))

(defn get-brave-roy-passives []
  (rest (map #(.text (.child % 1)) (.select (.child (.nextElementSibling (.parent (.first (.select (.select (.get (Jsoup/connect "https://feheroes.gamepedia.com/Roy:_Brave_Lion")) ".mw-parser-output") "#Passives")))) 0) "tr")))
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (get-brave-roy-passives))
)


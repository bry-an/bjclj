(ns bjclj.game)

(defn display-seen [card]
  (if (= true (:seen card)) "true" "false"))

(defn display-card
  [card]
  [:li {:key (:id card)} (:display card)])

(defn deal-click
  []
  (println "deal"))
  

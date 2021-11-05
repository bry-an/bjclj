(ns bjclj.game)

(defn display-card
  [card]
  [:li {:key (:id card)}(:display card)])

(defn deal-click
  []
  (println "deal"))
  

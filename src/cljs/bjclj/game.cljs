(ns bjclj.game)

(defn display-card
  [card]
  [:li {:key (:display card)}(:display card) (:belongs card)])

(defn deal-click
  []
  (println "deal"))
  

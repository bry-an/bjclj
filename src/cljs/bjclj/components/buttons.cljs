(ns bjclj.buttons
   (:require
     [bjclj.app-state :as state :refer [get-state]]))

(defn action-button [player]
  (cond
    (= 0 (state/hand-val @get-state player)) [:button {:on-click #(state/deal-initial-cards! player)} "Deal"]
    (> (state/hand-val @get-state :dealer) 11) [:button {:on-click state/reset} "Play Again"]
    (< (state/hand-val @get-state player) 21) [:button {:on-click #(state/hit player)} "Hit"]
    (> (state/hand-val @get-state :player1) 21) [:button {:on-click state/reset} "Play Again"]))

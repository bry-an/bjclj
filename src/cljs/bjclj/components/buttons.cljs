(ns bljclj.buttons
   (:require
     [bjclj.app-state :as state]))

(defn action-button [state player]
  (cond
    (= 0 (state/hand-val state player)) [:button {:on-click #(state/deal-initial-cards! player)} "Deal"]
    (> (state/hand-val state :dealer) 11) [:button {:on-click state/reset} "Play Again"]
    (< (state/hand-val state player) 21) [:button {:on-click #(state/hit player)} "Hit"]
    (> (state/hand-val state :player1) 21) [:button {:on-click state/reset} "Reset"]))

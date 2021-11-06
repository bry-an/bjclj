(ns bjclj.app-state
  (:require
     [bjclj.constants :as constants]
     [bjclj.util :as util]))

;; state config

(def app-state
  (atom
   {:deck (shuffle constants/deck)}
   {:game? false}))

;; getters

(defn hand-val [{:keys [deck]} player]
  (util/get-player-hand-val player deck))

(defn hand [{:keys [deck]} player]
  (util/get-player-hand player deck))

(defn did-player-win [state player]
  (let [p (hand-val state player)
        d (hand-val state :dealer)]
    (cond (> d p) nil 
          (> p d) true)))
;; mutations

(defn update-deck! [f & args]
  (apply swap! app-state update-in [:deck] f args))

(defn hit [player]
    (update-deck! (fn [deck]
                     (util/hit player deck))))
(defn reset []
  (update-deck! #(map util/reset-card %)))

(defn toggle-game []
  (swap! app-state update-in [:game] util/invert))

(defn deal-initial-cards! [player]
  (dotimes [n 2] (hit player)))

(defn resolve-game [state player]
  (if (< (hand-val state :dealer) 17) (resolve-game (hit :dealer))))
  

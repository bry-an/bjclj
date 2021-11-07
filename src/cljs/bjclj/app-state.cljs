(ns bjclj.app-state
  (:require
     [bjclj.constants :as constants]
     [bjclj.util :as util]
     [reagent.core :as r]))

;; state config

(def get-state
  (r/atom
   {:deck (shuffle constants/deck)
    :wins 0}))

;; getters

(defn hand-val [{:keys [deck]} player]
  (util/get-player-hand-val player deck))

(defn hand [{:keys [deck]} player]
  (util/get-player-hand player deck))

(defn unplayed-cards-count [deck]
  (count (filter #(= (:belongs %) :house) deck)))

(defn unplayed-cards []
  (r/track unplayed-cards-count (:deck @get-state)))

(defn dealer-greater-than-11 [state]
  (> (hand-val state :dealer) 11))

(defn game-finished? []
     @(r/track dealer-greater-than-11 @get-state))

(defn did-player-win [state player]
  (let [p (hand-val state player)
        d (hand-val state :dealer)]
    (cond (or
           (and
             (game-finished?)
             (> p d))
           (> d 21))
          true))) 

(defn player-won? [player]
  (r/track did-player-win @get-state player))

;; mutations

(defn update-deck! [f & args]
  (apply swap! get-state update-in [:deck] f args))

(defn update-wins! [f & args]
  (swap! get-state f args))

(defn hit [player]
    (update-deck! (fn [deck]
                     (util/hit player deck))))
(defn reset []
  (update-deck! #(map util/reset-card %)))

(defn deal-initial-cards! [player]
  (dotimes [n 2] (hit player)))

(defn resolve-game [state player]
  (if (< (hand-val state :dealer) 17) (resolve-game (hit :dealer))))

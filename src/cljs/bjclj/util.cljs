(ns bjclj.util)

(defn not-owned-pred [card]
  (= (:belongs card) :house))

(defn make-owned-pred [player]
  (fn [card]
    (=
     (:belongs card)
     player)))

(defn reset-card [card]
  (assoc card :belongs :house))

(def reset-all
  (partial map reset-card))
  

(defn assign-card-to-player [card-to-assign player]
  (fn [card]
    (if
      (=
       (:id card)
       (:id card-to-assign))
      (assoc card :belongs player)
      card)))
  
(defn hand-val-reducer [accum card]
      (+
       accum
       (:value card)))

(defn ace? [card]
  (=
    (:value card)
    1))

(defn has-aces? [hand]
  (> (count (filter ace? hand)) 0))

(def available-cards (partial filter not-owned-pred))

(defn hit [player deck]
  (let [available-cards (filter not-owned-pred deck)]
    (map (assign-card-to-player (first available-cards) player) deck)))
    
(defn get-player-hand [player deck]
  (filter (make-owned-pred player) deck))

(defn get-player-hand-val [player deck]
  (let [player-hand (get-player-hand player deck)
        raw-value (reduce hand-val-reducer 0 player-hand)]
    (if
      (and
        (< raw-value 12) 
        (has-aces? player-hand))
      (+ raw-value 10)
      raw-value)))

(defn hand-busted? [player deck]
  (> (get-player-hand-val player deck) 21))

(defn initiate-game [player deck]
  (->> deck
    (hit player)
    (hit player)))

(defn valid-hit [player deck]
  (< (get-player-hand-val player deck) 21))


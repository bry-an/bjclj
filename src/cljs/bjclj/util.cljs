(ns bjclj.util)

(def deck [{:id 1
            :display "ace of spades"
            :value 1
            :belongs 1}
           {:id 2
            :display "two of spades"
            :value 2
            :belongs 1}
           {:id 3
            :display "three of spades"
            :value 3
            :belongs 1}
           {:id 4
            :display "four of spades"
            :value 4
            :belongs 1}
           {:id 5
            :display "five of spades"
            :value 5
            :belongs 1}
           {:id 6
            :display "six of spades"
            :value 6
            :belongs 1}
           {:id 7 
            :display "seven of spades"
            :value 7
            :belongs 1}
           {:id 8 
            :display "eight of spades"
            :value 8
            :belongs 1}
           {:id 10 
            :display "nine of spades"
            :value 9
            :belongs 1}
           {:id 11 
            :display "ten of spades"
            :value 10 
            :belongs 1}
           {:id 12 
            :display "jack of spades"
            :value 10 
            :belongs 1}
           {:id 13 
            :display "queen of spades"
            :value 10 
            :belongs 1}
           {:id 14 
            :display "king of spades"
            :value 10 
            :belongs 1}
           {:id 15
            :display "ace of hearts"
            :value 1
            :belongs 1}
           {:id 16 
            :display "two of hearts"
            :value 2
            :belongs 1}
           {:id 17 
            :display "three of hearts"
            :value 3
            :belongs 1}
           {:id 18
            :display "four of hearts"
            :value 4
            :belongs 1}
           {:id 19
            :display "five of hearts"
            :value 5
            :belongs 1}
           {:id 20
            :display "six of hearts"
            :value 6
            :belongs 1}
           {:id 21 
            :display "seven of hearts"
            :value 7
            :belongs 1}
           {:id 22 
            :display "eight of hearts"
            :value 8
            :belongs 1}
           {:id 23 
            :display "nine of hearts"
            :value 9
            :belongs 1}
           {:id 24 
            :display "ten of hearts"
            :value 10 
            :belongs 1}
           {:id 25 
            :display "jack of hearts"
            :value 10 
            :belongs 1}
           {:id 26 
            :display "queen of hearts"
            :value 10 
            :belongs 1}
           {:id 27 
            :display "king of hearts"
            :value 10 
            :belongs 1}
           {:id 28 
            :display "ace of clubs"
            :value 1
            :belongs 1}
           {:id 29
            :display "two of clubs"
            :value 2
            :belongs 1}
           {:id 30
            :display "three of clubs"
            :value 3
            :belongs 1}
           {:id 31
            :display "four of clubs"
            :value 4
            :belongs 1}
           {:id 32
            :display "five of clubs"
            :value 5
            :belongs 1}
           {:id 33
            :display "six of clubs"
            :value 6
            :belongs 1}
           {:id 34 
            :display "seven of clubs"
            :value 7
            :belongs 1}
           {:id 35 
            :display "eight of clubs"
            :value 8
            :belongs 1}
           {:id 36 
            :display "nine of clubs"
            :value 9
            :belongs 1}
           {:id 37 
            :display "ten of clubs"
            :value 10 
            :belongs 1}
           {:id 38 
            :display "jack of clubs"
            :value 10 
            :belongs 1}
           {:id 39 
            :display "queen of clubs"
            :value 10 
            :belongs 1}
           {:id 40 
            :display "king of clubs"
            :value 10 
            :belongs 1}
           {:id 41
            :display "ace of diamonds"
            :value 1
            :belongs 1}
           {:id 42
            :display "two of diamonds"
            :value 2
            :belongs 1}
           {:id 43
            :display "three of diamonds"
            :value 3
            :belongs 1}
           {:id 44
            :display "four of diamonds"
            :value 4
            :belongs 1}
           {:id 45
            :display "five of diamonds"
            :value 5
            :belongs 1}
           {:id 46
            :display "six of diamonds"
            :value 6
            :belongs 1}
           {:id 47 
            :display "seven of diamonds"
            :value 7
            :belongs 1}
           {:id 48 
            :display "eight of diamonds"
            :value 8
            :belongs 1}
           {:id 49 
            :display "nine of diamonds"
            :value 9
            :belongs 1}
           {:id 50 
            :display "ten of diamonds"
            :value 10 
            :belongs 1}
           {:id 51 
            :display "jack of diamonds"
            :value 10 
            :belongs 1}
           {:id 52 
            :display "queen of diamonds"
            :value 10 
            :belongs 1}
           {:id 53 
            :display "king of diamonds"
            :value 10 
            :belongs 1}])

(defn not-owned-pred [card]
  (= (:belongs card) 1))

(defn make-owned-pred [player]
  (fn [card]
    (=
     (:belongs card)
     player)))

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

(defn hit [player deck]
  (let [available-cards (filter not-owned-pred deck)
        random-card (rand-nth available-cards)]
    (map (assign-card-to-player random-card player) deck)))
    
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

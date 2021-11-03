(ns bjclj.app-state)
(def app-state
  (r/atom
   {:hand []}))

(defn update-hand! [f & args]
  (apply swap! app-state update-in [:hand] f args))

(defn add-card! [c]
  (update-hand! conj c))

(defn remove-card! [c]
  (update-hand! (fn [cs]
                  (vec (remove #(= % c) cs)))
                c))

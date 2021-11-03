(ns bjclj.prod
  (:require [bjclj.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)

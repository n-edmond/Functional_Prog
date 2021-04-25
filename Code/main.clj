(use 'clojure.java.io)
(require ['clojure.string :as 'str])


;;Retrieved code from https://gist.github.com/baabelfish/6573984
(defn mrg [[x & xrest :as X] [y & yrest :as Y] R]
  (if (and (not-empty X) (not-empty Y))
    (if (<= x y)
      (mrg xrest Y (conj R x))
      (mrg X yrest (conj R y)))
    (concat R X Y)))


;;Retrieved code from https://gist.github.com/baabelfish/6573984
;;Where most info about clojure functions came from (map, apply) https://clojuredocs.org/clojure.core
(defn mergeSort [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (map mergeSort
                                        (split-at (/ (count input) 2) input)))))

;Info about pmap retrieved from https://clojuredocs.org/clojure.core/pmap
(defn mergeSort-2thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap mergeSort
                                         (split-at (/ (count input) 2) input)))))

(defn mergeSort-4thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap mergeSort-2thread
                                         (split-at (/ (count input) 2) input)))))

(defn mergeSort-8thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap mergeSort-4thread
                                         (split-at (/ (count input) 2) input)))))

(defn mergeSort-16thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap  mergeSort-8thread
                                          (split-at (/ (count input) 2) input)))))

(defn mergeSort-32thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap mergeSort-16thread
                                         (split-at (/ (count input) 2) input)))))

(defn mergeSort-64thread [input]
  (if (<  (count input) 2 ) input
                            (apply mrg
                                   (pmap mergeSort-64thread
                                         (split-at (/ (count input) 2) input)))))


;;Retrieved from https://stackoverflow.com/questions/55770730/trying-to-read-a-text-file-in-clojure-and-insert-the-data-into-a-list-or-a-vecto
(defn file-reader[inputfile]
  (let [file-contents (slurp inputfile)
        nums-as-strings   (str/split file-contents #" ")
        numbers           (map read-string nums-as-strings)]
    ;; Retrieved from time https://clojuredocs.org/clojure.core/time
    (time(mergeSort numbers ))
    (time(mergeSort-2thread numbers))
    (time(mergeSort-4thread numbers))
    (time(mergeSort-8thread numbers))
    (time(mergeSort-16thread numbers))
    (time(mergeSort-32thread numbers))
    (time(mergeSort-64thread numbers))
    ))

(file-reader "3000lines.txt")

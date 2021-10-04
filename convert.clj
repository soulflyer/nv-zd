#!/usr/bin/env bb

(require '[babashka.fs :as fs])

(def now (java.time.ZonedDateTime/now))
(def local-timezone (java.time.ZoneId/of "Asia/Saigon"))
(def pattern (java.time.format.DateTimeFormatter/ofPattern "yyMMddHHmm"))


(def create-time
  (.withZoneSameInstant
    now
    ;; (fs/file-time->instant
    ;;   (fs/creation-time "./test.txt"))
    local-timezone))

;;(fs/file-time->instant (fs/creation-time "./test.txt"))

(defn creation-date [file-name]
  (.format
    (java.time.LocalDateTime/ofInstant
      (fs/file-time->instant (fs/creation-time file-name))
      local-timezone)
    pattern))

(let [incoming-file (first *command-line-args*)
      filename (fs/file-name incoming-file)
      parent (.toString (fs/real-path (fs/parent incoming-file)))
      split (fs/split-ext incoming-file)
      ;;    ext (fs/extension incoming-file)
      ext (second split)
      basename (fs/file-name (first split))]

  (println parent)
  (println basename)
  (println ext)
  (println (creation-date incoming-file)))

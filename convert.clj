#!/usr/bin/env bb

(require '[babashka.fs :as fs])

(def local-timezone (java.time.ZoneId/of "Asia/Saigon"))
(def pattern (java.time.format.DateTimeFormatter/ofPattern "yyMMddHHmm"))

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
      ext (second split)
      basename (fs/file-name (first split))
      date-code (creation-date incoming-file)
      backup-dir (str parent "/old-notes/")
      old-name (.toString (fs/real-path incoming-file))
      new-name (str parent "/" date-code  " " basename ".org")]
  (if (= "txt" ext)
    (do (fs/copy old-name new-name)
        (when-not (fs/exists? backup-dir)
          (fs/create-dir backup-dir))
        (fs/move old-name backup-dir)
        (println "Wrote " new-name))
    (println "Not a txt file")))

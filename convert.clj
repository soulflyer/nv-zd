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

;; TODO re-write or wrap in elisp instead of clojure so I don't need to locate the file,
;; I can just run it directly from the deft buffer or the note itself.

(let [incoming-file (first *command-line-args*)
      path-length (count (fs/components incoming-file))
      parent (.toString (fs/real-path (if (= 1 path-length)
                                        "."
                                        (fs/parent incoming-file))))
      split (fs/split-ext incoming-file)
      ext (second split)
      basename (fs/file-name (first split))
      date-code (creation-date incoming-file)
      backup-dir (str parent "/old-notes/")
      old-name (.toString (fs/real-path incoming-file))
      new-name (str date-code " " basename ".org")
      new-path (str parent "/" new-name)]
  (if (= "txt" ext)
    (do
      (spit new-path (str "#+TITLE: " basename "\n# TAGS\n\n"))
      (spit new-path (slurp old-name) :append true)
      (when-not (fs/exists? backup-dir)
        (fs/create-dir backup-dir))
      (fs/move old-name backup-dir)
      (print new-name))
    (println "Not a txt file")))

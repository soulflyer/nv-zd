#!/usr/bin/env bb

(require '[babashka.fs :as fs])
;;(require '[clojure.java-time :as time])

;; # NV_DIR="/Users/iain/Documents/org-mode/NotationalVelocity/"

;; # zetteldeft_id () {
;; #     stat -f "%SB" -t"%y%m%d%H%M" $1
;; # }

;; # file_name () {
;; #     local original_file_name=$1
;; #     local bare_file_name=`basename $original_file_name .txt`
;; #     local zdid=$(stat -f "%SB" -t"%y%m%d%H%M" "$1")
;; #     echo $zdid$bare_file_name
;; # }
;; # file_name $1
;; # # ls $NV_DIR

;; 2109111923

(* 7 8)

(def now (java.time.ZonedDateTime/now))
(def local-timezone (java.time.ZoneId/of "Asia/Saigon"))
(def pattern (java.time.format.DateTimeFormatter/ofPattern "yymmHHmm"))


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

(println (creation-date "./test.txt"))

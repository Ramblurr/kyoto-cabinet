(ns user (:use kyoto-cabinet.core))

; the file extension is significant
; seeh ttp://fallabs.com/kyotocabinet/javadoc/kyotocabinet/DB.html#open(java.lang.String, int) 
(with-cabinet { :filename "test.kch" :mode (+ OWRITER OCREATE) }
  (doseq [[name val] [["1" "one"] ["2" "two"] ["3" "three"]]]
    (put-value name val)))

; mode defaults to OREADER
(with-cabinet { :filename "test.kch" }
  (println (get-value "1")))

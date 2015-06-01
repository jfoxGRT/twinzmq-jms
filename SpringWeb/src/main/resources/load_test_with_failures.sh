#!/bin/sh

TEST_QUOTES=("Test" "FailBeforePreMsg" "failAfterPreMsg" "failAfterJdbcInsert" "failAfterPostMsg" "failPreAck" "failPostAck")

declare -i NUM_QUOTES=${#TEST_QUOTES[@]}
declare -i INDEX=0

while [ $INDEX -lt 50001 ]
do
   ARRAY_INDEX=$[ 0 + $[ $RANDOM % 6 ]]
   QUOTE=${TEST_QUOTES[$ARRAY_INDEX]}
   curl -X POST -d "quoteText=$QUOTE&rating=5" 127.0.0.1:8080/SpringWebNew-0.0.1-SNAPSHOT/quoteSave
   INDEX=$(( $INDEX + 1 ))
done
echo "\n$QUOTE test"
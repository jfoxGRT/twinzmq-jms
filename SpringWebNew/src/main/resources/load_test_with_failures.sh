#!/bin/sh

TEST_QUOTES=("Test" "FailBeforePreMsg" "failAfterPreMsg" "failAfterJdbcInsert" "failAfterPostMsg")
QUOTE_COUNTS=(0 0 0 0 0)

declare -i NUM_QUOTES=${#TEST_QUOTES[@]}
declare -i INDEX=0

while [ $INDEX -lt 50001 ]
do
   ARRAY_INDEX=$[ 0 + $[ $RANDOM % 5 ]]
   QUOTE=${TEST_QUOTES[$ARRAY_INDEX]}
   QUOTE_COUNTS[$ARRAY_INDEX]=$(( ${QUOTE_COUNTS[$ARRAY_INDEX]} + 1 ))
   curl -X POST -d "quoteText=$QUOTE&rating=5" 127.0.0.1:8080/SpringWebNew-0.0.1-SNAPSHOT/quoteSave
   INDEX=$(( $INDEX + 1 ))
done

INDEX=0

while [ $INDEX -lt 5 ]
do
   echo "\n\n${TEST_QUOTES[$INDEX]}: Count = ${QUOTE_COUNTS[$INDEX]}"
   INDEX=$(( $INDEX + 1 ))
done

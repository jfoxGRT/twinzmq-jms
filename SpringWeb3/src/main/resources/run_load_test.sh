#!/bin/sh

declare -i INDEX=2

while [ $INDEX -lt 6 ]
do
   exec /src/twinzmq-to-jms/SpringWeb/src/main/resources/load_test.sh $INDEX 1&>2 load_test_results_raw_output.txt
done


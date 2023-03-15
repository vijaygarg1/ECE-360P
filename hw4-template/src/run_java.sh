#!/bin/bash

rm -rf /src/out/output/.?* /src/out/output/* 2> /dev/null
rmdir /src/out/output 2> /dev/null
javac -cp "/opt/spark/jars/*" *.java
jar -cf wordgraph.jar *.class
/opt/spark/bin/spark-submit --class WordGraph --master spark://`hostname`:7077 --conf spark.eventLog.enabled=true ./wordgraph.jar /src/data/novel /src/out/output

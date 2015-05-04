#!/bin/bash
OUTPUT_PATH="/user/chrisho/output/sparkle"
hadoop fs -rm -r -skipTrash -skipTrash "$OUTPUT_PATH"

#./build.sh

pig -latest -Dmapred.job.queue.name=search_prod -Dmapreduce.job.acl-view-job=* \
-Dfs.permissions.umask-mode=000 \
-Dmapred.map.tasks.speculative.execution=true \
-Dmapred.cluster.reduce.memory.mb=4096 \
-param input="/user/csferng/spark/spark.V20141201.0.TW/datapack/" \
-param output="$OUTPUT_PATH" \
-param default_parallel=1024 \
-f parse_sparkle.pig

hadoop fs -chmod -R 755 "$OUTPUT_PATH"
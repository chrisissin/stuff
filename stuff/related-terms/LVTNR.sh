#!/bin/bash
rm -rf pig_*log
#hadoop fs -rm -r -skipTrash -skipTrash "/user/chrisho/output/test"

pig -x local -Dmapred.job.queue.name=search_prod -Dmapreduce.job.acl-view-job=* \
-Dfs.permissions.umask-mode=000 \
-Dmapred.map.tasks.speculative.execution=true \
-Dmapred.cluster.reduce.memory.mb=4096 \
-param input="/homes/chrisho/trending_wf/test_cat/related/part-m-02586" \
-param output="/homes/chrisho/trending_wf/test_cat/related/output/test" \
-param lib_path="/homes/gossip/mingtian/workflows/spam_detection/daily/workflow/lib" \
-f VTNRelated.pig

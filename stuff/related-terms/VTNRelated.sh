#!/bin/bash
rm -rf pig_*log
hadoop fs -rm -r -skipTrash -skipTrash "/user/chrisho/output/test"

pig -Dmapred.job.queue.name=search_prod -Dmapreduce.job.acl-view-job=* \
-Dfs.permissions.umask-mode=000 \
-Dmapred.map.tasks.speculative.execution=true \
-Dmapred.cluster.reduce.memory.mb=4096 \
-param input="/data/restricted/data/search_ult_pty/20141129/search_Taiwan-Kimo/" \
-param output="/user/chrisho/output/test" \
-param lib_path="/homes/gossip/mingtian/workflows/spam_detection/daily/workflow/lib" \
-param topq=" /user/chrisho/output/trends_benzene/tw/201412020600/part-r-00000#smallfile" \
-f VTNRelated.pig
   
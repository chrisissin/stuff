register /homes/gossip/mingtian/workflows/spam_detection/daily/workflow/lib/string.jar;
REGISTER /homes/gossip/mingtian/workflows/spam_detection/daily/workflow/lib/shasta_apps.jar;
register /homes/gossip/mingtian/workflows/spam_detection/daily/workflow/lib/nrti.jar;
--%DEFAULT input '/homes/lmwang/tmp/spam_filter/input'
--%DEFAULT output '/homes/lmwang/tmp/spam_filter/output'

%DEFAULT normalizers        'unicode_nfc,space';

DEFINE ULTLOADER com.yahoo.yst.sds.ULT.ULTLoader();
DEFINE QUERY_NORMALIZER com.yahoo.search.shasta.udf.normalizer.DispatcherNormalizer('$normalizers');
DEFINE BCOOKIE_TIMESTAMP_DECODER com.yahoo.search.shasta.udf.decoder.BCookieTimestampDecoder();

--set default_parallel 300;
sds = LOAD '$input' USING ULTLOADER AS (simpleFields:map[], mapFields:map[], mapListFields:map[]);

records = FOREACH sds
  GENERATE
    (CHARARRAY) simpleFields#'bcookie' AS bcookie,
    --(LONG) simpleFields#'timestamp' AS timestamp,
    --(LONG) (simpleFields#'bcookie_age' IS null 
    --? BCOOKIE_TIMESTAMP_DECODER((CHARARRAY) simpleFields#'bcookie') 
    --: simpleFields#'bcookie_age') AS bcookie_timestamp, 
    (CHARARRAY) simpleFields#'type' AS sds_type,
    (CHARARRAY) mapFields#'page_params'#'query' AS query,
    --(INT) simpleFields#'src_spaceid' AS spaceid,
    --(CHARARRAY) simpleFields#'referrer' AS referrer,
    --(CHARARRAY) mapFields#'page_params'#'frcode' AS frcode,
    --(CHARARRAY) mapFields#'page_params'#'fr2' AS fr2,
    (CHARARRAY) mapFields#'clickinfo'#'targurl' AS targurl,
    (CHARARRAY) mapFields#'clickinfo'#'title' AS title
;

--topq = LOAD '/user/chrisho/output/trends_benzene/tw/topq.txt' USING PigStorage() AS (
--trend:chararray,
--score:float,a:int,b:int,c:int,d:int,e:int,f:int,g:int
--);

REGISTER /homes/chrisho/trending_wf/test_cat/related/VTNRelated.jar;
--'/user/chrisho/output/trends_benzene/tw/201412020600/part-r-00000'
DEFINE VTNRelated com.yahoo.search.gossip.VTNRelated('$topq'); 
--records = group record;
--topq = group topq by trend;
--result = FILTER records {
--                    topq = LOAD '/user/chrisho/output/trends_benzene/tw/topq.txt' USING PigStorage() AS (
--                            trend:chararray,
--                            score:float,a:int,b:int,c:int,d:int,e:int,f:int,g:int
--                        );
--                    filres = FOREACH topq {
--                        FILTER records BY(string.LASTINDEXOF(records.query, topq.trend) != -1 )AND targurl IS NOT NULL;
--                        }

--                    generate VTNRelated(
--                        records, 
--                        filres);
--                }
--filter l by $0 > 1;                
--VTNRelated(records, topq.trend);                
--FILTER records BY(string.LASTINDEXOF(query, topq.trend) != -1 )AND targurl IS NOT NULL;                
--FILTER records BY(string.LASTINDEXOF(query, topq.trend) != -1 )AND targurl IS NOT NULL;        
result = foreach records generate VTNRelated(targurl, title);              
--dump result;
result = group result by ($0);
result = foreach result generate group;
dump result;
--STORE result INTO '$output';

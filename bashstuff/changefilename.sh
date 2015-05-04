for  i in default*;
do
 j=`/bin/echo $i | /bin/sed -e 's/default/queryalsotry/'` 
# echo $ji
 mv $i $j
done
#mv {,new.}original.filename
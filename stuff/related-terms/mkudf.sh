#!/bin/bash
rm -rf com
javac -d . -classpath /homes/lmwang/gossip/lib/libyell_java.jar:/home/gs/pig/current/lib/pig.jar:/home/gs/hadoop/current/share/hadoop/common/hadoop-common-2.5.0.3.1409091448.jar:/home/gs/hadoop/current/share/hadoop/common/lib/commons-logging-1.1.3.jar *.java
jar cvf VTNRelated.jar com/
mv VTNRelated.jar lib/VTNRelated.jar

#/lmwang/gossip/lib/libyell_java.jar

#!/bin/bash

./build.sh

rm -rf /homes/chrisho/gossip/output
pig -x local \
-param input="head.data" \
-param output="output" \
-f parse_sparkle.pig
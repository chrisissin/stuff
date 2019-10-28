#!/bin/bash

REPO="$3"

if [ -z "$1" ] || [ -z "$2" ] || || [ -z "$3" ]; then
	echo "usage: "
	echo "  $0 [branch/name] [travis-token] [REPO]"
	echo "  Get your token here: https://travis-ci.com/account/preferences"
fi

body="{
  \"request\": {
    \"branch\":\"$1\",
    \"config\": {
      \"branches\":{
        \"only\": [\"$1\"]
      }
    }
  }
}"

curl -s -X POST \
   -H "Content-Type: application/json" \
   -H "Accept: application/json" \
   -H "Travis-API-Version: 3" \
   -H "Authorization: token $2" \
   -d "$body" \
   "https://api.travis-ci.com/repo/explodingbarrel%2F$REPO/requests"

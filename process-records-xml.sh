#!/usr/bin/env bash

set -eo pipefail

curl -v \
  -X POST \
  -H "Content-Type:application/xml" \
  -H "Accept:application/json" \
  -d "@input/records.xml" \
  "http://localhost:8080/validate"

curl -v \
  -H "Content-Type:multipart/form-data" \
  -H "Accept:application/json" \
  -F "csvRecords=@input/records.csv" \
  "http://localhost:8080/validate"
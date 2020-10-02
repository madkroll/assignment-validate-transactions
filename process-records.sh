#!/usr/bin/env bash

set -eo pipefail

echo "# Verifying XML processing"
curl -v \
  -X POST \
  -H "Content-Type:application/xml" \
  -H "Accept:application/json" \
  -d "@input/records.xml" \
  "http://localhost:8080/validate"

echo "# Verifying CSV processing"
curl -v \
  -H "Content-Type:multipart/form-data" \
  -H "Accept:application/json" \
  -F "csvRecords=@input/records.csv" \
  "http://localhost:8080/validate"
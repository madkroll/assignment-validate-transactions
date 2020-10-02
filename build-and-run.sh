#!/usr/bin/env bash

set -eo pipefail

echo "# Building project"
mvn clean package

echo "# Running service"
nohup java -jar validate-transactions-web/target/validate-transactions-web-1.0.0-SNAPSHOT.jar &
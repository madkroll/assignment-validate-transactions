# Build
```shell script
mvn clean package
```

# Run
```shell script
# Run as a nohup process
# Requires 8080 port to be available
nohup java -jar validate-transactions-web/target/validate-transactions-web-1.0.0-SNAPSHOT.jar &
```

# Test XML upload
```shell script
# make sure location is pointing on XML file with records
curl -v \
  -X POST \
  -H "Content-Type:application/xml" \
  -H "Accept:application/json" \
  -d "@input/records.xml" \
  "http://localhost:8080/validate"
```

# Test CSV upload
```shell script
curl -v \
  -H "Content-Type:multipart/form-data" \
  -H "Accept:application/json" \
  -F "csvRecords=@input/records.csv" \
  "http://localhost:8080/validate"
```
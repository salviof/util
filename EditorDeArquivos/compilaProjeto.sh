find . -name target -type d -exec rm -rf {} \;
mvn clean install  -Dmaven.test.skip=true





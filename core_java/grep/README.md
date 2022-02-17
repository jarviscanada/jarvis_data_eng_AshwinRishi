# Introduction

The Grep Applications searches for a specific string in the file and outputs the matched line to a different file. Grep App uses Getters and Setters pattern along with maven to perform operation along with OOPS concepts with the lambda's being used in the stream of operations.

# Quick Start

There are two ways to run this application:

1. Jar Approach:

```bash
# JAR file approach
java -jar grep-1.0-SNAPSHOT.jar ".*Romeo.*Juliet.*" ./data ./out/grep_result.txt
```

2. Docker Approach:

```bash
# Docker image approach
docker_user=your_docker_id

docker login -u ${docker_user} --password-stdin

docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log ${docker_user}/grep "Romeo" /data/txt/ ./out/

```

The Grep application will make use of the regex(Romeo) to search in the folder(/data/txt) and write the output to the (./out) file if there is a match of lines.

# Implementation

This section defines the implementation of the Grep application.

## Pseudocode

The process involves in search for the files, followed by reading each line in each file. For each line read, the system searches for the regex pattern and stores it to the list and later which is being written to an output file.

```
//Process method pseudocode
matched lines = []; //String array to hold all matching lines retrieved from files

for file in listFiles(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)

writeToFile(matchedLines)
```

## Performance Issue

The Grep app has a runtime of o(n2). So in the larger system of file folder given, the system may hold to runtime exceptions. Moreover, Since the application uses List collections to write the matched string, it might lead to java.lang—OutOfMemoryError for larger systems of services.

# Test

I have used Junits to test the files and have achieved the following coverage:

1. Implementation class coverage:![](https://img.shields.io/badge/JavaGrepImp-93%25-green)
2. Junit line Coverage:![Junit coverage](https://img.shields.io/badge/JavaGrepImpTest-100%25-red)

# Deployment

Have been deployed into Docker for minimal distribution of usages. Docker Images can be used across multiple platforms to access the application. The Docker and application have been hoisted in Github for easy access.
The below steps can be used to create an image and deploy the same in DockerHub.

```bash
#Create dockerfile
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

#Pakcage your java app
mvn clean package

#build a new docker image locally
docker build -t ${docker_user}/grep .

#verify your image
docker image ls | grep "grep"

#run docker container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log ${docker_user}/grep "Romeo" /data/txt/ ./out/

```

# Improvement

- We can use Builder Pattern instead of getters and setters for code injections which ensures chaining of methods and data security.
- Validating the input strings in the POJO classes can be implemented.
- Instead of LOG4j, we can use different logging jars.

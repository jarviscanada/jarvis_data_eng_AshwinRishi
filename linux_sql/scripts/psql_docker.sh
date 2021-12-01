#! /bin/sh

#The cmd,db_username,db_password are used to capture command line arguments.
cmd=$1
db_username=$2
db_password=$3

#This statements works on logical-OR(||).If the status of the docker fails or return error it executes the second part of command (starts the docker).
sudo systemctl status docker || systemctl start docker

#It evaluates status of the container and returns an JSON details of the container.
docker container inspect jrvs-psql
container_status=$?

#User switch case to handle create|stop|start operations.
case $cmd in
  create)

  #Validates if the container is already created. if so exits with the error.
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  #Validates if the cli has valid arguments else exits with error code.
  if [ $# -ne 3 ]; then
    echo 'Create/start/stop requires username and password'
    exit 1
  fi

  #Creates a new container using psql image with name called jrvs-psql.
  #Assigns postgre password with the $3 and dbname with $2 of the Cli argument.
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -d -v $db_username:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
	exit $?
	;;

  start|stop)
  #Checks if the instance status equals 1; else exits with container has not been created error.
  if [ $container_status -eq 1 ]; then
    echo 'container is not created'
    exit 1
  fi

  #This step perform the required operation given at $1 of the cli argument.
	docker container $cmd jrvs-psql
	exit $?
	;;

  *)
  #This code executes if the user specifies apart from start,stop or create operation.
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac
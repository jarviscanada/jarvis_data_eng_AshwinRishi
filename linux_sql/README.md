# Monitoring Agent Program
This Project is an Agent which monitors and collects both hardware specification data and resource usage data of each Node in the server.

# Introduction
The Monitoring Agent Program was developed to help the LCA (The Jarvis Linux Cluster Administration). The LCA team has a specific set of bash scripts/files being run on the server. Our program helps to reveal the hardware information and resource usage to these servers. Additionally, with the help of the crontab command, the bash runs for every minute and inserts the data in PostgreSQL. Below is the set of sub-sections to glance more at the Project.

1. Quick Start (Instructions to set and run the Project).
2. Implementation(overview on how the program has been implemented)
	- Architecture
	- Scripts
	- Database Modelling.
3. Testing(Methods and tools used to test the program)
4. Deployment(Instructions on deployment/ tool used to deploy) 
5. Improvements(Future updates on the Project)
# Quick Start
Kindly follow the steps to install the Project and run the Monitoring Agent:
1. -   On GitHub.com, navigate to the main page of the repository.
    
-   To clone the repository using the command line using HTTPS, under "Quick setup," click
-   Open Git Bash.
-   Change the current working directory to the location where you want the cloned directory.
-   Type `git clone,` and then paste the URL you copied earlier.
    
    ```shell
    $ git clone https://github.com/jarviscanada/jarvis_data_eng_AshwinRishi.git
    ```
    
-   Press **Enter** to create your local clone.
    
    ```shell
    $ git clone https://github.com/jarviscanada/jarvis_data_eng_AshwinRishi.git
    > Cloning into `jarvis_data...
    > remote: Counting objects: 10, done.
    > remote: Compressing objects: 100% (8/8), done.
    > remove: Total 10 (delta 1), reused 10 (delta 1)
    
     > Unpacking objects: 100% (10/10), done.
    ```
2.    Create a Container where we can install our scripts later:
```
psql_docker.sh create psql_username psql_password
```
3.   Starting the container:
```
psql_docker.sh start
```
4. Postgres Schema to connect with a database name **host_agent
```
psql -h localhost -U postgres -d host_agent -f ddl.sql    
```
5. Executes host_info to insert the hardware usage to DB:
```
bash scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```
6. The host_usage script inserts the server usage data into the database:
```
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```
8.  The below CRON program helps to execute the host_usage script for every minute:
```
crontab -e
# add following line to the file
* * * * * bash /home/centos/dev/jarvis_data_eng_AshwinRishi/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres "password"> /tmp/host_usage.log
```

# Implementation
## Architecture
![Test Image 4]([https://github.com/jarviscanada/jarvis_data_eng_AshwinRishi/blob/develop/linux_sql/assets/Architecture.jpg])

## Scripts

1. psql_docker.sh - sets up a SQL instance using docker, which can be used in the local machine during the host_info and host_usage data collection and storing.
2.  host_info.sh- The script collects hardware specification data and then inserts the data into the SQL instance. The hardware specifications are static, so the script will be executed only once.
3.  host_usage.sh- The script collects server usage data and then inserts the data into the SQL database. The script will be executed every minute using Linux crontab.
4. crontab - The crontab executes the SQL for every minute.
5. queries.sql -This SQL script handles three queries in the Monitoring Agent Program. The first query displays CPU's memory and usage. It helps us to visualize the information of hardware resources in the Node. The second query shows the average memory used over 5-minutes intervals.

## Database Modeling
![Test Image 1]([https://github.com/jarviscanada/jarvis_data_eng_AshwinRishi/blob/develop/linux_sql/assets/sqlDataModel.jpg])
The Postgres database (host_agent) contains two tables that are used to store the monitoring data:

### host_info

This table handles the host hardware information. This table has a primary key that helps us to identify the distinct user-id. This table contains information about the CPU (number, architecture, model, speed, cache) as well as the total amount of memory in the system.

Column | Type | Description
--------------|------|--------------
Id | `SERIAL` | This field is of primary type key with serial to assign values automatically.
hostname | `VARCHAR` | The name of the user server is stored in this field.
cpu_number | `integer` | Hold the number of CPUs in the server.
cpu_architecture | `VARCHAR` | Information about CPU architecture eg: x86_64
cpu_model | `VARCHAR` | Information about the CPU model: Intel(R) Xeon(R) CPU @ 2.30GHz
cpu_mhz | `REAL` | Contains the clock speed of the CPU.
L2_cache | `INTEGER` | Holds the size of the L2 cache in the CPU.
total_mem | `VARCHAR` |Information about the total memory installed in the server.
Timestamp | `TIMESTAMP` | Hold the time at which the bash script inserts the data.

### host_usage

This table handles the host usage information. The table
contains information about the memory free in the system, the CPU used information, disk IO, and disk available.

Column | Type | Description
--------------|------|--------------
Timestamp | `TIMESTAMP` | TimeStamp handles the insertion time of the data in UTI format.
host_id | `INTEGER` | Foreign key to the Host_info table.
memory_free | `INTEGER` | Handles about the free memory.
cpu_idle | `INTEGER` | Percentage of the CPU idle time.
cpu_kernel | ` INTEGER ` | Information about the percentage of CPU usage.
disk_io | `INTEGER` | Holds the amount of disk I/O currently being used.
disk_available | `INTEGER` | Validates the disk available space in the CPU.

# Test
`bash -x ./script.sh` is used for debugging with all the parameters and validated locally to check the exceptions. 

# Deployment
1. All the program scripts are deployed in the GitHub repository by creating a local Instance of Postgres with the help of Docker Image. 
2.  Additionally, Cron Tab is used to run the file every minute locally and upload it to the Postgre SQL, which can be visualized by connecting to the host_agent database.

# Improvements
1. Failure to insert data to the PostgreSQL in the host_info script can be implemented in the future.
2. Front End development or Exporting the Scripts as Rest APIs can be implemented in the future for further efficient monitoring of data.
3. Visualizing the inserted data in the front end as chart's/graphs' will be a helpful factor for the LCA Administrator. 


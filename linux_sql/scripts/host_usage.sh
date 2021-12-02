#! /bin/bash

#The psql_host,port and db name with user and password captures the command line arguments.
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Check if number of arguments is valid
if [ $# -ne 5 ]; then
    echo "Invalid number of arguments"
    echo "Usage: host port db_name username password"
    exit 1
fi

#This instruction Saves machine statistics in MB and current machine hostname to a variable.
lscpu_out=$(lscpu)
vmstat_mb=$(vmstat --unit M)
df_out=$(df -BM /)
hostname=$(hostname -f)

#These instructions retrieves the hardware specifications.
memory_free=$(echo "$vmstat_mb" | awk '{print $4 }' | sed -n 3p)
cpu_idle=$(echo "$vmstat_mb" | awk 'NR==3 {print $15 }' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $14}' | sed -n 3p)
disk_io=$(echo "$(vmstat -d)" | awk '{print 10}' | sed -n 3p)
disk_available=$(echo "$df_out" | awk '{print $4}' | sed -n 2p | sed 's/.$//')

#time in the UTC format: eg: `2019-11-26 14:40:19`
timestamp="$(date '+%Y-%m-%d %H:%M:%S')"

#PSQL command: creates a command to insert server usage data into host_usage table.
insert_statement="INSERT INTO host_usage
          (timestamp,host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
          VALUES
          ('$timestamp',(SELECT id FROM host_info WHERE hostname='$hostname'), $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)"

#sets up the env var for pql cmd
export PGPASSWORD=$psql_password

#Inserts the data into a host_usage table
psql -h "$psql_host" -p "$sql_port" -U "$psql_user" -d "$db_name" -c "$insert_statement"
exit $?
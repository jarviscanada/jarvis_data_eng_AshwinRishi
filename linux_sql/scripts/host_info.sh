#! /bin/sh

#Assigning CLI argument to a variable.
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#check if the number of arguments are valid
if [ $# -ne 5 ]; then
    echo "Invalid number of arguments"
    exit 1
fi

#This instruction Saves machine statistics in MB and current machine hostname to a variable.
hostname="$(hostname -f)"
lscpu_out="$(lscpu)"

#These instructions retrieves the hardware specifications.
cpu_number="$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)"
cpu_architecture="$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)"
cpu_model="$(echo "$lscpu_out" | egrep "^Model\sname:" | awk '{print $3,$4,$5,$6,$7}' | xargs)"
cpu_mhz="$(echo "$lscpu_out" | egrep "^CPU\sMHz:" | awk '{print $3}' | xargs)"
l2_cache="$(echo "$lscpu_out" | grep '^L2 cache' | cut -f 2 -d ":" | awk '{$1=$1}1' | cut -f 1 -d "K")"
total_mem="$(grep MemTotal /proc/meminfo | awk '{print $2}')"
timestamp="$(date '+%Y-%m-%d %H:%M:%S')"

#inserts the hardware specification to the host_info table
insert_statement="insert into host_info (hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,l2_cache,total_mem,timestamp)values('$hostname',$cpu_number,'$cpu_architecture','$cpu_model','$cpu_mhz','$l2_cache',$total_mem,'$timestamp')"
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_statement"

exit $?

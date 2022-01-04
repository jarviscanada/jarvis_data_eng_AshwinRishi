-- Query to group hosts by hardware info
SELECT
    cpu_number,
    id AS host_id,
    total_mem
FROM
    host_info
GROUP BY
    cpu_number,
    id
ORDER BY
    total_mem DESC;

-- Function to round of the TimeStamp with interval 5 Minutes.
CREATE FUNCTION roundTimeStamp(ts timestamp) RETURNS TIMESTAMP AS $ $ BEGIN RETURN date_trunc('hour', ts) + date_part('minute', ts) :: INT / 5 * interval '5 min';

END;

$ $ LANGUAGE PLPGSQL;

-- Query to return Average Memory usage.
SELECT
    host_id,
    hostInfo.hostname,
    roundTimeStamp(hostUsage.timestamp) AS timestamp,
    CAST(
        AVG(
            (hostInfo.total_mem - hostUsage.memory_free) * 100 / total_mem
        ) AS INT
    ) avg_used_mem_percentage
FROM
    host_info hostInfo
    INNER JOIN host_usage hostUsage ON hostInfo.id = hostUsage.host_id
GROUP BY
    roundTimeStamp(hostUsage.timestamp),
    host_id,
    hostname;

-- Query to retreive server details failing to insert 3 rows in a 5 minutes interval.
SELECT
    DISTINCT host_id,
    roundTimeStamp(host_usage.timestamp) AS timeStamps,
    COUNT(*) AS points
FROM
    host_usage
GROUP BY
    timeStamps,
    host_id
HAVING
    count(*) <= 3;
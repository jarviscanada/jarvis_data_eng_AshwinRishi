/*
 * Author: Ashwin Rishi.
 * Purpose: creates table named host_info if not present in the current database.
 * This table handles on user/node information details in the cluster of network
 */
CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
    id SERIAL PRIMARY KEY NOT NULL,
    hostname VARCHAR NOT NULL UNIQUE,
    cpu_number INTEGER NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model VARCHAR NOT NULL,
    cpu_mhz REAL NOT NULL,
    l2_cache VARCHAR NOT NULL,
    total_mem INTEGER NOT NULL,
    "timestamp" timestamptz NOT NULL
);

/*
 * Author: Ashwin Rishi.
 * Purpose: creates table named host_usage if not present in the current database
 * host_usage table handles the server information on every minute.
 */
CREATE TABLE if not exists PUBLIC.host_usage (
    "timestamp" TIMESTAMP NOT NULL,
    host_id INT NOT NULL,
    memory_free INTEGER NOT NULL,
    cpu_idle INTEGER NOT NULL,
    cpu_kernel INTEGER NOT NULL,
    disk_io INTEGER NOT NULL,
    disk_available INTEGER NOT NULL,
    CONSTRAINT fk_host_id FOREIGN KEY(host_id) REFERENCES host_info(id)
);
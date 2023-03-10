#!/bin/sh

mkdir /tmp/spark-events
/opt/spark/sbin/start-history-server.sh
/opt/spark/bin/spark-class org.apache.spark.deploy.master.Master --host `hostname` --webui-port 8080 &
/opt/spark/bin/spark-class org.apache.spark.deploy.worker.Worker --cores 1 --webui-port 9091 "`hostname`:7077" &
/opt/spark/bin/spark-class org.apache.spark.deploy.worker.Worker --cores 1 --webui-port 9092 "`hostname`:7077"&
/opt/spark/bin/spark-class org.apache.spark.deploy.worker.Worker --cores 1 --webui-port 9093 "`hostname`:7077" &
/opt/spark/bin/spark-class org.apache.spark.deploy.worker.Worker --cores 1 --webui-port 9094 "`hostname`:7077" &
/bin/bash

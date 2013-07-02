#!/bin/sh

rm -rf apache-tomcat-7.0.41/webapps/a
rm -rf apache-tomcat-7.0.41/webapps/b
cp a/target/a.war apache-tomcat-7.0.41/webapps/
cp b/target/b.war apache-tomcat-7.0.41/webapps/
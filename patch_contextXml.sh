#!/bin/sh

perl -p -i.bak -e 's/<Context>/<Context crossContext=\"true\">/g' apache-tomcat-7.0.41/conf/context.xml
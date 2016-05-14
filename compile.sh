#!/bin/sh


#start 
rm -rf ./classes/*
find /home/jiahuichen/AminerData/AminerAuthorParser/ -name *.java > ./javaFiles.txt
echo "create javafiles"

#rm ./After/*.csv
javac -d ./classes @./javaFiles.txt


#!/bin/sh

rm -rf ./classes/*
#rm ./After/*.csv
javac -d ./classes @../javaFiles.txt


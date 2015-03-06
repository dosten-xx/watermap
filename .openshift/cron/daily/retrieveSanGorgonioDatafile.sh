#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
curl -o $od/datafile.txt http://www.howlingduck.com/triterra/gorgonio/datafile.txt

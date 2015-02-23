#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
wget -o $od/sang.log http://www.howlingduck.com/triterra/gorgonio/datafile.txt

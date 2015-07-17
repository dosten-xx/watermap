#!/bin/bash

od=$OPENSHIFT_DATA_DIR
doc=https://docs.google.com/spreadsheets/d/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/pub
echo "od=$od"
curl -o $od/pct-CA-A.htm -G -d gid=0 $doc
#curl -o $od/pct-CA-C.htm -G -d gid=2 $doc
#curl -o $od/pct-CA-E.htm -G -d gid=3 $doc
#curl -o $od/pct-CA-M.htm -G -d gid=5 $doc
#curl -o $od/pct-OR-B.htm -G -d gid=6 $doc

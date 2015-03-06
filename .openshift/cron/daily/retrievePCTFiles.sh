#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
curl -sk -o $od/pct-a.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=0
#wget https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=0
curl -sk -o $od/pct-c.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=2
curl -sk -o $od/pct-e.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=3

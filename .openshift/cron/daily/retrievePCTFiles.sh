#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
wget -O $od/pct-a.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=0
wget -O $od/pct-c.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=2
wget -O $od/pct-e.htm https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc&gid=3

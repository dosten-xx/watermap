#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
curl -o $od/pct-a.htm -G -d gid=0 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-c.htm -G -d gid=2 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-e.htm -G -d gid=3 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc

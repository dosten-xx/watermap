#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
curl -o $od/pct-CA-A.htm -G -d gid=0 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-CA-C.htm -G -d gid=2 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-CA-E.htm -G -d gid=3 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-CA-M.htm -G -d gid=5 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc
curl -o $od/pct-OR-B.htm -G -d gid=6 https://docs.google.com/spreadsheet/pub?key=0AnjydhFdh1E2dEtQWEFXOGVHeWtsQVlCSnFXcTh2VXc

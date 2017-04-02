#!/bin/bash

od=$OPENSHIFT_DATA_DIR
apik=$GOOGLE_SHEETS_API_KEY
# old way curl -o $od/pct-CA-A.htm -G -d gid=0 $doc
curl -o $od/pct-CA-A.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Campo%20-%20Idyllwild!A11:G1000?key=$apik
curl -o $od/pct-CA-C.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Idyllwild%20-%20Agua%20Dulce!A11:G1000?key=$apik
curl -o $od/pct-CA-E.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Agua%20Dulce%20-%20Cottonwood%20Pass!A11:G100?key=$apik
curl -o $od/pct-CA-M.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Northern%20CA!A9:G1000?key=$apik
curl -o $od/pct-OR-B.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Oregon!A9:G1000?key=$apik
curl -o $od/pct-WA-H.json https://sheets.googleapis.com/v4/spreadsheets/1gEyz3bw__aPvNXpqqHcs7KRwmwYrTH2L0DEMW3RbHes/values/Washington!A9:G1000?key=$apik

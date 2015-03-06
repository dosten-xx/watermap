#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
curl -o ca_section_a_gps.zip http://www.pctmap.net/pctdownloads/ca_section_a_gps.zip
unzip -o ca_section_a_gps.zip
mv ca_section_a_gps/* .
rm -rf ca_section_a_gps

curl -o ca_section_b_gps.zip http://www.pctmap.net/pctdownloads/ca_section_b_gps.zip
unzip -o ca_section_b_gps.zip
mv ca_section_b_gps/* .
rm -rf ca_section_b_gps

curl -o ca_section_c_gps.zip http://www.pctmap.net/pctdownloads/ca_section_c_gps.zip
unzip -o ca_section_c_gps.zip
mv ca_section_c_gps/* .
rm -rf ca_section_c_gps

curl -o ca_section_d_gps.zip http://www.pctmap.net/pctdownloads/ca_section_d_gps.zip
unzip -o ca_section_d_gps.zip
mv ca_section_d_gps/* .
rm -rf ca_section_d_gps

curl -o ca_section_e_gps.zip http://www.pctmap.net/pctdownloads/ca_section_e_gps.zip
unzip -o ca_section_e_gps.zip
mv ca_section_e_gps/* .
rm -rf ca_section_e_gps

curl -o ca_section_f_gps.zip http://www.pctmap.net/pctdownloads/ca_section_f_gps.zip
unzip -o ca_section_f_gps.zip
mv ca_section_f_gps/* .
rm -rf ca_section_f_gps

curl -o ca_section_g_gps.zip http://www.pctmap.net/pctdownloads/ca_section_g_gps.zip
unzip -o ca_section_g_gps.zip
mv ca_section_g_gps/* .
rm -rf ca_section_g_gps

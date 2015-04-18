#!/bin/bash

od=$OPENSHIFT_DATA_DIR
echo "od=$od"
cd $od
wget http://www.pctmap.net/pctdownloads/ca_section_a_gps.zip
unzip -o ca_section_a_gps.zip
mv ca_section_a_gps/* .
rm -rf ca_section_a_gps

wget http://www.pctmap.net/pctdownloads/ca_section_b_gps.zip
unzip -o ca_section_b_gps.zip
mv ca_section_b_gps/* .
rm -rf ca_section_b_gps

wget http://www.pctmap.net/pctdownloads/ca_section_c_gps.zip
unzip -o ca_section_c_gps.zip
mv ca_section_c_gps/* .
rm -rf ca_section_c_gps

wget http://www.pctmap.net/pctdownloads/ca_section_d_gps.zip
unzip -o ca_section_d_gps.zip
mv ca_section_d_gps/* .
rm -rf ca_section_d_gps

wget http://www.pctmap.net/pctdownloads/ca_section_e_gps.zip
unzip -o ca_section_e_gps.zip
mv ca_section_e_gps/* .
rm -rf ca_section_e_gps

wget http://www.pctmap.net/pctdownloads/ca_section_f_gps.zip
unzip -o ca_section_f_gps.zip
mv ca_section_f_gps/* .
rm -rf ca_section_f_gps

wget http://www.pctmap.net/pctdownloads/ca_section_g_gps.zip
unzip -o ca_section_g_gps.zip
mv ca_section_g_gps/* .
rm -rf ca_section_g_gps

wget http://www.pctmap.net/pctdownloads/ca_section_m_gps.zip
unzip -o ca_section_m_gps.zip
mv ca_section_m_gps/* .
rm -rf ca_section_m_gps

wget http://www.pctmap.net/pctdownloads/ca_section_n_gps.zip
unzip -o ca_section_n_gps.zip
mv ca_section_n_gps/* .
rm -rf ca_section_n_gps

wget http://www.pctmap.net/pctdownloads/ca_section_o_gps.zip
unzip -o ca_section_o_gps.zip
mv ca_section_o_gps/* .
rm -rf ca_section_o_gps

wget http://www.pctmap.net/pctdownloads/ca_section_p_gps.zip
unzip -o ca_section_p_gps.zip
mv ca_section_p_gps/* .
rm -rf ca_section_p_gps

wget http://www.pctmap.net/pctdownloads/ca_section_q_gps.zip
unzip -o ca_section_q_gps.zip
mv ca_section_q_gps/* .
rm -rf ca_section_q_gps

wget http://www.pctmap.net/pctdownloads/ca_section_r_gps.zip
unzip -o ca_section_r_gps.zip
mv ca_section_r_gps/* .
rm -rf ca_section_r_gps

rm ca_section_*_gps.zip.*

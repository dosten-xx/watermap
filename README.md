# Watermap - check it out on [OpenShift](http://backcountrywater.info/).

## For testing:
* Set environment variable ```OPENSHIFT_DATA_DIR=./src/test/resources```
* Run ```mvn clean install```

## To deploy locally:
* Start wildfly (tested on 10.1.0.Final)
 - Set env var ```OPENSHIFT_DATA_DIR=/mydevdir/watermap/src/test/resources```, or something like this to point to the test data in the source code
* Run ```mvn clean install wildfly:deploy```

## Eclipse JUnit testing:
* Run configuration:
 - env var
 - sys prop

## Environment Variables used:
 - ```OPENSHIFT_DATA_DIR``` : location of data files (e.g. /data) : no default
 - ```APP_URL``` : hostname and context of application (e.g. backcountrywater.info) : default is localhost
 - ```OPENSHIFT_APP_PORT``` : application port (e.g. 80) : default is 80

## Troubleshooting
 - If the maps don't show up, disable HTTPSEverywhere plugin (at least for openstreetmap.org)

Watermap - check it out on [OpenShift](http://backcountrywater.info/).

For testing:
* Set environment variable OPENSHIFT_DATA_DIR=./src/test/resources
* Run mvn clean install

To deploy locally:
* Start wildfly (tested on 8.2.0.Final)
* Run mvn clean install wildfly:deploy

Eclipse JUnit testing:
* Run configuration:
 - env var
 - sys prop

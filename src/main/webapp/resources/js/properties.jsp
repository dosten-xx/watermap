<%@ page language="java" contentType="text/javascript; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
var hostName = 'backcountrywater.info';
var hostPort = '<%=(System.getenv("OPENSHIFT_APP_PORT") != null ? System.getenv("OPENSHIFT_APP_PORT") : "80") %>';

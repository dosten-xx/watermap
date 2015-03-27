<%@ page language="java" contentType="text/javascript; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
var hostName = '<%=System.getenv("OPENSHIFT_APP_DNS") %>';
var hostPort = '<%=(System.getenv("OPENSHIFT_APP_PORT") != null ? System.getenv("OPENSHIFT_APP_PORT") : "80") %>';

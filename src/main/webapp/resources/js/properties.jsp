<%@ page language="java" contentType="text/javascript; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
var hostName = '<%=(System.getenv("APP_URL") != null ? System.getenv("APP_URL") : "localhost") %>';
var hostPort = '<%=(System.getenv("OPENSHIFT_APP_PORT") != null ? System.getenv("OPENSHIFT_APP_PORT") : "80") %>';

<%@ page import="net.osten.watermap.convert.*" %>
<%@ page import="net.osten.watermap.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!doctype html>
<html>
<head>
<title>Map Examples</title>
<link rel="stylesheet" href="/resources/ol3/ol.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/samples.css" type="text/css" />
<link rel="stylesheet" href="/resources/bootstrap/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="/resources/bootstrap/bootstrap-responsive.min.css" type="text/css">
<style type="text/css">
#map {
	position: relative;
}

#popup {
	padding-bottom: 45px;
}
</style>
</head>
<body>
	<div id="map" class="map"></div>
	<div id="popup" class="popup"></div>
	<script src="http://code.jquery.com/jquery-1.11.2.min.js" type="text/javascript"></script>
	<script src="/resources/bootstrap/bootstrap.min.js" type="text/javascript"></script>
	<script src="/resources/ol3/ol-debug.js" type="text/javascript"></script>
	<script type="text/javascript">

	// put iconStyles in a separate js file
	var iconStyleHigh = new ol.style.Style({
		image : new ol.style.Icon(({
			anchorXUnits : 'fraction',
			anchorYUnits : 'pixels',
			opacity : 0.75,
			src : '/resources/img/icon-high.png'
		}))
	});

	var iconStyleMed = new ol.style.Style({
		image : new ol.style.Icon(({
			anchorXUnits : 'fraction',
			anchorYUnits : 'pixels',
			opacity : 0.75,
			src : '/resources/img/icon-medium.png'
		}))
	});

	var iconStyleLow = new ol.style.Style({
		image : new ol.style.Icon(({
			anchorXUnits : 'fraction',
			anchorYUnits : 'pixels',
			opacity : 0.75,
			src : '/resources/img/icon-low.png'
		}))
	});

	var iconStyleDry = new ol.style.Style({
		image : new ol.style.Icon(({
			anchorXUnits : 'fraction',
			anchorYUnits : 'pixels',
			opacity : 0.75,
			src : '/resources/img/icon-dry.png'
		}))
	});

	var iconStyleUnknown = new ol.style.Style({
		image : new ol.style.Icon(({
			anchorXUnits : 'fraction',
			anchorYUnits : 'pixels',
			opacity : 0.75,
			src : '/resources/img/icon-unknown.png'
		}))
	});
	
<%
SanGorgonioReport converter = new SanGorgonioReport();
converter.setFilePath(System.getenv("OPENSHIFT_DATA_DIR") + File.separator + "datafile.txt");
System.out.println("sang file=" + System.getenv("OPENSHIFT_DATA_DIR") + "datafile.txt");
Set<WaterReport> results = converter.convert();

int i = 0;
StringBuffer featureList = new StringBuffer();

for (WaterReport wr : results) { %>
var point<%=i %> = new ol.geom.Point([<%=wr.getLat() %>, <%=wr.getLon() %> ]);
var geom<%=i %> = point<%=i %>.transform('EPSG:4326', 'EPSG:3857');
var iconFeature<%=i %> = new ol.Feature({
	geometry : geom<%= i%>,
	name : '<%=wr.getName() %>',
	state : '<%=wr.getState() %>',
	description : "<%=wr.getDescription() %>",
	status : '<%=wr.getState() %>',
	lastReport : '<%=wr.getLastReport() %>',
	source : '<%=wr.getSource() %>'
});

<%
String state = "Unknown";
if (wr.getState() == WaterState.HIGH) {
      state = "High";
}
else if (wr.getState() == WaterState.MEDIUM) {
   state = "Medium";
}
else if (wr.getState() == WaterState.LOW) {
   state = "Low";
}
else if (wr.getState() == WaterState.DRY) {
   state = "Dry";
}
%>
iconFeature<%=i %>.setStyle(iconStyle<%=state %>);

<%
	if (i > 0) {
	   featureList.append(",");
	}
	featureList.append("iconFeature" + i);
	
	i++;
}
%>

var vectorSource = new ol.source.Vector({
	features : [ <%=featureList %> ]
});

	</script>
	<script src="/resources/js/main.js" type="text/javascript"></script>
</body>
</html>

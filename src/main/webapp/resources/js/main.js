//****************************
// Global variables
// ****************************
var map;
var mapLayers;

//****************************
// Custom controls
// ****************************

window.app = {};
var app = window.app;

app.generateTrailControl = function(opt_options) {
	//alert("creating new control...");
	var options = opt_options || {};

	var select = document.createElement('select');
	select.id = 'trailSelect';
	select.className = 'trailSelect';
	select.innerHTML = '<option value="0">All</option><option value="1">San Gorgonio</option><option value="2">PCT</option><option value="3">San Mateo Canyon</option>';

	var this_ = this;
	var getTrail = function(e) {
		// prevent #export-geojson anchor from getting appended to the url
		e.preventDefault();
		//alert("in getTrail e=" + e);
		var selectedTrail = document.getElementById('trailSelect');
		//alert("selected trail=" + selectedTrail.value);
		var currentLayerCount = map.getLayers().getLength();
		alert("map has " + currentLayerCount + " layers");
		
		// 0th layer is the map layer

		// clear all trail layers
		for (var i = 1; i < currentLayerCount; i++) {
			map.removeLayer(mapLayers[i]);
		}
		
		if (selectedTrail.value == 0) {
			// show all trails
			for (var i = 1; i < mapLayers.length; i++) {
				map.addLayer(mapLayers[i]);
			}
		}
		else {
			// show just one trail
			// hide the value'th layer (i.e. 1=SanG, 2=PCT) in the map
			// zoom to that trail
			alert("adding layer " + selectedTrail.value);
			map.addLayer(mapLayers[selectedTrail.value]);
		}
	};

	select.addEventListener('change', getTrail, false);

	var anchor = document.createElement('a');
	anchor.id = 'trailSelect';
	anchor.className = 'trailSelect';
	anchor.href = 'about.html';
	anchor.innerHTML = 'About';

	var element = document.createElement('div');
	element.className = 'export-geojson ol-unselectable';
	element.appendChild(select);
	element.appendChild(anchor);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
};

ol.inherits(app.generateTrailControl, ol.control.Control);


//****************************
// Vector layer styles
//****************************

var alpha = 0.4;

var colorBlue = [0, 0, 255, alpha];
var colorYellow = [255, 255, 0, alpha];
var colorRed = [255, 0, 0, alpha];
var colorWhite = [255, 255, 255, alpha];

var styleCache = {};

styleCache['DRY'] = new ol.style.Style({
	//text: feature.get('name'), // TODO DEO add name to text style
	image : new ol.style.Circle({
		radius : 5,
		stroke : new ol.style.Stroke({
			color : colorRed,
			width : 1.25
		}),
		fill : new ol.style.Fill({
			color : colorRed
		})
	}),
	fill : new ol.style.Fill({
		color : colorRed
	}),
	stroke : new ol.style.Stroke({
		color : colorRed,
		width : 1.25
	})
});

styleCache['LOW'] = new ol.style.Style({
	image : new ol.style.Circle({
		radius : 5,
		stroke : new ol.style.Stroke({
			color : colorRed,
			width : 1.25
		}),
		fill : new ol.style.Fill({
			color : colorRed
		})
	}),
	fill : new ol.style.Fill({
		color : colorRed
	}),
	stroke : new ol.style.Stroke({
		color : colorRed,
		width : 1.25
	})
});

styleCache['MEDIUM'] = new ol.style.Style({
	image : new ol.style.Circle({
		radius : 5,
		stroke : new ol.style.Stroke({
			color : colorYellow,
			width : 1.25
		}),
		fill : new ol.style.Fill({
			color : colorYellow
		})
	}),
	fill : new ol.style.Fill({
		color : colorYellow
	}),
	stroke : new ol.style.Stroke({
		color : colorYellow,
		width : 1.25
	})
});

styleCache['HIGH'] = new ol.style.Style({
	image : new ol.style.Circle({
		radius : 5,
		stroke : new ol.style.Stroke({
			color : colorBlue,
			width : 1.25
		}),
		fill : new ol.style.Fill({
			color : colorBlue
		})
	}),
	fill : new ol.style.Fill({
		color : colorBlue
	}),
	stroke : new ol.style.Stroke({
		color : colorBlue,
		width : 1.25
	})
});

styleCache['UNKNOWN'] = new ol.style.Style({
	image : new ol.style.Circle({
		radius : 5,
		stroke : new ol.style.Stroke({
			color : colorWhite,
			width : 1.25
		}),
		fill : new ol.style.Fill({
			color : colorWhite
		})
	}),
	fill : new ol.style.Fill({
		color : colorWhite
	}),
	stroke : new ol.style.Stroke({
		color : colorWhite,
		width : 1.25
	})
});

function wrStyle(feature, resolution) {
	var state = feature.get('state');
	return [styleCache[state]];
};

//****************************
// water report vector layers
//****************************

var sangSource = new ol.source.GeoJSON({
	projection: 'EPSG:3857',
	url : 'http://' + hostName + ':' + hostPort + '/rest/sang'
});

var sangLayer = new ol.layer.Vector({
	source: sangSource,
	style: wrStyle 
});

var pctSource = new ol.source.GeoJSON({
	projection: 'EPSG:3857',
	url : 'http://' + hostName + ':' + hostPort + '/rest/pct'
});

var pctLayer = new ol.layer.Vector({
	source: pctSource,
	style: wrStyle
});

var sanmateoSource = new ol.source.GeoJSON({
	projection: 'EPSG:3857',
	url : 'http://' + hostName + ':' + hostPort + '/rest/sanmateowild'
});

var sanmateoLayer = new ol.layer.Vector({
	source: sanmateoSource,
	style: wrStyle
});


//****************************
// OSM layer
//****************************

var osmLayer = new ol.layer.Tile({
	source : new ol.source.OSM()
});

// EPSG:4326 == WGS 84
var initCenter = ol.proj.transform([ -116.853084, 34.121955 ], 'EPSG:4326', 'EPSG:3857');

var view = new ol.View({
	center : initCenter,
	zoom : 10
});

mapLayers = [ osmLayer, sangLayer, pctLayer, sanmateoLayer ];

map = new ol.Map({
	target : 'map',
	layers : [ osmLayer, sangLayer, pctLayer, sanmateoLayer ],
   controls: ol.control.defaults().extend([
       new app.generateTrailControl({source: null})
     ]),
	view : view
});


// ****************************
// popup handling
// ****************************

var element = document.getElementById('popup');

var popup = new ol.Overlay({
	element : element,
	positioning : 'bottom-center',
	stopEvent : false
});
map.addOverlay(popup);

// display popup on click
map.on('click', function(evt) {
	var feature = map.forEachFeatureAtPixel(evt.pixel, function(feature, layer) {
		return feature;
	});
	if (feature) {
		var geometry = feature.getGeometry();
		var coord = geometry.getCoordinates();
		popup.setPosition(coord);
		// DEO need to destroy here to ensure the content is refreshed when
		// clicked directly
		// from one feature to another
		$(element).popover('destroy');
		$(element).popover({
			'placement' : 'right',
			'html' : true,
			'title' : feature.get('name'),
			'content' : feature.get('state') + '<br />' + feature.get('location') + '<br />' + feature.get('description') + '<br />Last Report:' + formatDate(feature.get('lastReport')) + '<br />Source: ' + '<a target=\'_new\' href=\'' + feature.get('url') + '\'>' + feature.get('source') + '</a>'
		});
		$(element).popover('show');
	} else {
		$(element).popover('destroy');
	}
});

// change mouse cursor when over marker
map.on('pointermove', function(e) {
	if (e.dragging) {
		$(element).popover('destroy');
		return;
	}
	var pixel = map.getEventPixel(e.originalEvent);
	var hit = map.hasFeatureAtPixel(pixel);
	var mapTarget = document.getElementById(map.getTarget());
	mapTarget.style.cursor = hit ? 'pointer' : '';
});

function formatDate(date)
{
	if (date) {
		return new Date(date).toLocaleDateString();
	}
	
	return 'n/a';
}

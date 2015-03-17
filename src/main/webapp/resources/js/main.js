//****************************
// Custom controls
// ****************************

window.app = {};
var app = window.app;

app.generateTrailControl = function(opt_options) {
	alert("creating new control...");
	var options = opt_options || {};

	var select = document.createElement('select');
	select.id = 'trailSelect';
	select.className = 'trailSelect';
	select.innerHTML = '<option>San Gorgonio</option><option>PCT</option>';

	var this_ = this;
	var getTrail = function(e) {
		// prevent #export-geojson anchor from getting appended to the url
		e.preventDefault();
		alert("in getTrail e=" + e);
		var selectedTrail = document.getElementById('trailSelect');
		alert("selected trail=" + selectedTrail.value);
		//var source = options.source;
		//var format = new ol.format.GeoJSON();
		//var features = source.getFeatures();
		//var featuresGeoJSON = format.writeFeatures(features);
		//download('export.geojson', JSON.stringify(featuresGeoJSON));
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

app.generateAboutControl = function(opt_options) {
	alert("creating new about control...");
	var options = opt_options || {};

	var anchor = document.createElement('a');
	anchor.id = 'trailSelect';
	anchor.className = 'trailSelect';
	anchor.innerHTML = 'About';

	var element = document.createElement('div');
	element.className = 'export-geojson ol-unselectable';
	element.appendChild(anchor);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
};

ol.inherits(app.generateAboutControl, ol.control.Control);

//****************************
// Vector layer styles
//****************************

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

var styleCache = {};

var wrStyleStroke = new ol.style.Stroke({
	color : [255, 50, 50, 0.4],
	width : 1.25
});

var wrStyleFill = new ol.style.Fill({
	color : [255, 50, 50, 0.4]
});

function wrStyle(feature, resolution) {
	var state = feature.get('state');
	// TODO DEO set default - if (!state) { show default }
	if (!styleCache[state]) {
		styleCache[state] = new ol.style.Style({
			//text: feature.get('name'), // TODO DEO add name to text style
			image : new ol.style.Circle({
				radius : 5,
				stroke : wrStyleStroke,
				fill : wrStyleFill
			}),
			fill : wrStyleFill,
			stroke : wrStyleStroke
		})
	}
	
	return [styleCache[state]];
};

//****************************
// water report vector layers
//****************************

// TODO DEO need to configure url's (not hard-coded)

var sangSource = new ol.source.GeoJSON({
	projection: 'EPSG:3857',
	//url : 'http://watermap-ostennet.rhcloud.com/rest/sang'
	//url : 'http://localhost:8080/rest/sang'
	url : 'http://' + hostName + ':' + hostPort + '/rest/sang'
});

var sangLayer = new ol.layer.Vector({
	source: sangSource,
	style: wrStyle 
});

var pctSource = new ol.source.GeoJSON({
	projection: 'EPSG:3857',
	//url : 'http://watermap-ostennet.rhcloud.com/rest/pct'
	//url : 'http://localhost:8080/rest/sang'
	url : 'http://' + hostName + ':' + hostPort + '/rest/pct'
});

var pctLayer = new ol.layer.Vector({
	source: pctSource
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

var map = new ol.Map({
	target : 'map',
	layers : [ osmLayer, sangLayer, pctLayer ],
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
			'content' : feature.get('state') + '<br />' + feature.get('location') + '<br />' + feature.get('description') + '<br />' + feature.get('lastReport') + '<br />' + '<a target=\'_new\' href=\'' + feature.get('url') + '\'>' + feature.get('source') + '</a>'
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

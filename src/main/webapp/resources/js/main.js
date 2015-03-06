// ****************************
// Custom controls
// ****************************

window.app = {};
var app = window.app;

/**
 * @constructor
 * @extends {ol.control.Control}
 * @param {Object=}
 *           opt_options Control options.
 */
app.generateGeoJSONControl = function(opt_options) {
	//alert("creating new control...");
	var options = opt_options || {};

	var anchor = document.createElement('select');
	anchor.id = 'trailSelect';
	anchor.className = 'trailSelect';
	anchor.innerHTML = '<option>San Gorgonio</option><option>PCT</option>';

	var this_ = this;
	var getGeoJSON = function(e) {
		// prevent #export-geojson anchor from getting appended to the url
		e.preventDefault();
		alert("in getGeoJSON e=" + e);
		var selectedTrail = document.getElementById('trailSelect');
		alert("selected trail=" + selectedTrail.value);
		//var source = options.source;
		//var format = new ol.format.GeoJSON();
		//var features = source.getFeatures();
		//var featuresGeoJSON = format.writeFeatures(features);
		//download('export.geojson', JSON.stringify(featuresGeoJSON));
	};

	anchor.addEventListener('change', getGeoJSON, false);
	//anchor.addEventListener('touchstart', getGeoJSON, false);

	var element = document.createElement('div');
	element.className = 'export-geojson ol-unselectable';
	element.appendChild(anchor);

	ol.control.Control.call(this, {
		element : element,
		target : options.target
	});
};

ol.inherits(app.generateGeoJSONControl, ol.control.Control);


//****************************
// map init
//****************************

var vectorLayer = new ol.layer.Vector({
	source : vectorSource
});

var layer = new ol.layer.Tile({
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
	layers : [ layer, vectorLayer ],
   controls: ol.control.defaults().extend([
       new app.generateGeoJSONControl({source: null})
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
			'content' : feature.get('state') + '<br />' + feature.get('description') + '<br />' + feature.get('lastReport') + '<br />' + '<a target=\'_new\' href=\'' + feature.get('url') + '\'>' + feature.get('source') + '</a>'
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

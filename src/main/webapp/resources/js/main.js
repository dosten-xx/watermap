
var vectorLayer = new ol.layer.Vector({
	source : vectorSource
});

// the normal setup for our samples
var layer = new ol.layer.Tile({
	source : new ol.source.OSM()
});

var london = ol.proj.transform([ -116.853084,34.121955 ], 'EPSG:4326', 'EPSG:3857');
// var london = ol.proj.transform([0,0], 'EPSG:4326', 'EPSG:3857');

var view = new ol.View({
	center : london,
	zoom : 10
});

var map = new ol.Map({
	target : 'map',
	layers : [ layer, vectorLayer ],
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
		// DEO need to destroy here to ensure the content is refreshed when clicked directly
		// from one feature to another
		$(element).popover('destroy');
		$(element).popover({
			'placement' : 'right',
			'html' : true,
			'content' : feature.get('name') + '<br />'
				+ feature.get('state') + '<br />'
				+ feature.get('description') + '<br />'
				+ feature.get('lastReport') + '<br />'
				+ feature.get('source')
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

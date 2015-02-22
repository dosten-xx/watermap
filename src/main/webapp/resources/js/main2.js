// 32.600579978898168" lon="-116.47007997147739"
var pt1 = new ol.geom.Point([ -116.400799, 32.60057997 ]);
var pt11 = pt1.transform('EPSG:4326', 'EPSG:3857');
var iconFeature = new ol.Feature({
	geometry : pt11,
	name : 'WR001',
	description : 'Juvenile center. Faucet behind sign.',
	status : 'Good'
});

var pt2 = new ol.geom.Point([ -116.50446002371609, 32.60907000862062 ]);
var pt22 = pt2.transform('EPSG:4326', 'EPSG:3857');
var iconFeature2 = new ol.Feature({
	geometry : pt22,
	name : 'WR002',
	description : 'Test description',
	status : 'Good'
});

var iconStyle = new ol.style.Style({
	image : new ol.style.Icon(({
		anchor : [ 0.5, 46 ],
		anchorXUnits : 'fraction',
		anchorYUnits : 'pixels',
		opacity : 0.75,
		src : '/watermap/resources/img/icon.png'
	}))
});

var iconStyle2 = new ol.style.Style({
	image : new ol.style.Icon(({
		anchor : [ 0.5, 46 ],
		anchorXUnits : 'fraction',
		anchorYUnits : 'pixels',
		opacity : 0.75,
		src : '/watermap/resources/img/flag.png'
	}))
});

iconFeature.setStyle(iconStyle);
iconFeature2.setStyle(iconStyle2);

var vectorSource = new ol.source.Vector({
	features : [ iconFeature, iconFeature2 ]
});

var vectorLayer = new ol.layer.Vector({
	source : vectorSource
});

// the normal setup for our samples
var layer = new ol.layer.Tile({
	source : new ol.source.OSM()
});

var london = ol.proj.transform([ -116.401766, 32.673525 ], 'EPSG:4326', 'EPSG:3857');
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
			'content' : feature.get('name') + '<br />' + feature.get('description')
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

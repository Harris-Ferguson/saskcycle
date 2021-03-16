// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
script.async = true;

var map;
var infowindow;
var servce;

// Attach your callback function to the `window` object
window.initMap = function() {
    var saskatoon = new google.maps.LatLng(52.118, -106.643)

    infowindow = new google.maps.InfoWindow();

    map = new google.maps.Map(document.getElementById("map"), {
        center: saskatoon,
        zoom: 10,
    });

    var request = {
        query : 'Recycle',
        fields : ['name', 'geometry']
    };

    var service = new google.maps.places.PlacesService(map);

    service.findPlaceFromQuery(request, function(results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            for (var i = 0; i < results.length; i++) {
                createMarker(results[i], results[i].name);
                console.log(results[i]);
            }
            map.setCenter(results[0].geometry.location);
        }
    });
};

function createMarker(place, name) {
    new google.maps.Marker({
        position: place.geometry.location,
        title: name,
        map: map
    });
}

// Append the 'script' element to 'head'
document.head.appendChild(script);
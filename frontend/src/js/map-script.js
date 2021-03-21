// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
script.async = true;

var map;

window.markers = [];

// Attach your callback function to the `window` object
window.initMap = function(){
        var saskatoon = new google.maps.LatLng(52.118, -106.643)

        map = new google.maps.Map(document.getElementById("map"), {
            center: saskatoon,
            zoom: 10,
        })
        console.log(markers);
        for(var i = 0 ; i < window.markers.length ; i++){
            window.renderMarker(window.markers[i].lat, window.markers[i].long, window.markers[i].name);
        }
};

window.renderMarker = function(lat, long, name) {
    let pos = new google.maps.LatLng(lat, long);
    new google.maps.Marker({
        position: pos,
        title: name,
        map: map
    });
};

window.addMarker = function(lat, long, name) {
    let marker = {
        lat: lat,
        long: long,
        name: name
    }
    window.markers.push(marker);
};

document.head.appendChild(script);

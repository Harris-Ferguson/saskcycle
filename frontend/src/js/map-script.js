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

    map = new google.maps.Map(document.getElementById("map"), {
        center: saskatoon,
        zoom: 10,
    });
};

window.addMarker = function(lat, long, name){
    console.log(lat, long, name);
    let pos = new google.maps.LatLng(lat, long);
    new google.maps.Marker({
        position: pos,
        title: name,
        map: map
    });
};

// Append the 'script' element to 'head'
document.head.appendChild(script);
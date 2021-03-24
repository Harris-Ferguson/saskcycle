/*
Usage for Directions API Code given by Google at:
https://developers.google.com/maps/documentation/javascript/examples/directions-simple#maps_directions_simple-javascript
*/

// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
script.async = true;



var map;

window.markers = [];

/* Uses callback function to set up event handling for route planning, Attach to winder*/
window.initMap = function(){

    // Set the Google Directions services and renderers for interacting with the map
    const dService = new google.maps.DirectionsService();
    const dRenderer = new google.maps.DirectionsRenderer();

    // Set up map from view to focus on Saskatoon coordinates
    var saskatoon = new google.maps.LatLng(52.118, -106.643)
    map = new google.maps.Map(document.getElementById("map"), {
        center: saskatoon,
        zoom: 10,
    })
    dRenderer.setMap(map);

    // Display all markers
    console.log(markers);
    for(var i = 0 ; i < window.markers.length ; i++){
        window.renderMarker(window.markers[i].lat, window.markers[i].long, window.markers[i].name);
    }

    // Hook javascript to GUI button - render directions upon submission
    var submitButton = document.getElementById("submitStart");
    const eventHandler = function(){
        calculateAndDisplayRoute(dService, dRenderer);
    }
    submitButton.addEventListener("click", eventHandler);

}

/* --------- Marker Scripts ---------- */

window.renderMarker = function(lat, long, name) {
    let pos = new google.maps.LatLng(lat, long);
    new google.maps.Marker({
        position: pos,
        title: name,
        map: map
    });
};

window.addMarker = function(lat, long, name){
    let marker = {
        lat: lat,
        long: long,
        name: name
    }
    window.markers.push(marker);
};

/* --------- Route Scripts ---------- */

function test(){
alert(document.getElementById("sCoords").value)
}


function calculateAndDisplayRoute(directionsService, directionsRenderer){
    alert("hey!");
  directionsService.route(
    {
      origin: {
        query: document.getElementById("text").value,
      },
      destination: {
        query: document.getElementById("sCoords").value,
      },
      travelMode: google.maps.TravelMode.DRIVING,
    },
    (response, status) => {
      if (status === "OK") {
        directionsRenderer.setDirections(response);
      } else {
        window.alert("Directions request failed due to " + status);
      }
    }
  );
}

document.head.appendChild(script);

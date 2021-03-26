/*
Usage for Directions API Code given by Google at:
https://developers.google.com/maps/documentation/javascript/examples/directions-simple#maps_directions_simple-javascript

Additional help with conversion between localDateTime and java.util.Date from:
https://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date
*/

// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
script.async = true;


// The google map object
var map;
// A list of every marker to be placed on the Google Map
window.markers = [];

/* --------- Initialization Script ---------- */

/* Uses callback function to set up event handling for route planning, Attach to winder*/
window.initMap = function(){

    // Set the Google Directions services and renderers for interacting with the map
    const dService = new google.maps.DirectionsService();
    const dRenderer = new google.maps.DirectionsRenderer();

    // Set up map from view to focus on Saskatoon coordinates
    var saskatoon = new google.maps.LatLng(52.118, -106.643)
    map = new google.maps.Map(document.getElementById("map"), {
        center: saskatoon,
        zoom: 11,
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
    if (submitButton != null) submitButton.addEventListener("click", eventHandler);
}


/* --------- Marker Scripts ---------- */

/*
* Creates a new Google Maps marker
*/
window.renderMarker = function(lat, long, name) {
    let pos = new google.maps.LatLng(lat, long);
    new google.maps.Marker({
        position: pos,
        title: name,
        map: map
    });
};

/*
* Adds a marker to the list of markers to render
*/
window.addMarker = function(lat, long, name){
    let marker = {
        lat: lat,
        long: long,
        name: name
    }
    window.markers.push(marker);
};

/* --------- Route Scripts ---------- */

/*
* Queries GoogleMaps to update the current map with new directions
*/
function calculateAndDisplayRoute(directionsService, directionsRenderer){

    // Due to the limitations of Vaadin (returns int instead of text for selectors,
    // we must hard code the values selected from the GUI selector
    var indexOfTransSelection = document.getElementById("trans").value;
    var transSelection;
    if (indexOfTransSelection == 1)  transSelection = "WALKING";
    else if (indexOfTransSelection == 2) transSelection = "BICYCLING";
    else if (indexOfTransSelection == 3) transSelection = "TRANSIT";
    else if (indexOfTransSelection == 4) transSelection = "DRIVING";

    // Convert departTime (LocalDateTime) to Date object for GoogleAPI use
    var departTime = document.getElementById("timePick").value;
    if (departTime != null) {
        departTime = new Date(departTime);
    }

    // Add "Saskatoon" to query name if Saskatoon not already in name
    var startingLocation = document.getElementById("text").value;
    if (!startingLocation.includes("saskatoon") || !startingLocation.includes("Saskatoon")) {
        startingLocation = startingLocation.concat(" Saskatoon");
   }

  // Make GoogleMaps query
  directionsService.route(
    {
      origin: {
        query: startingLocation,
      },
      destination: {
        query: document.getElementById("sCoords").value,
      },
      travelMode: google.maps.TravelMode[transSelection],
      transitOptions: {
        departureTime: departTime
      }
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
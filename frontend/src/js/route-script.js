////// Create the script tag, set the appropriate attributes
////var routeScript = document.createElement('script');
////routeScript.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
////routeScript.async = true;
//
//var map;
//
//window.markers = [];
//
///*
//Usage for Directions API Code given by Google at:
//https://developers.google.com/maps/documentation/javascript/examples/directions-simple#maps_directions_simple-javascript
//*/
//
//
///* Uses callback function to set up event handling for route planning, Attach to winder*/
//window.initMap = function(){
//
//    // Set the Google Directions services and renderers for interacting with the map
//    const directionsService = new google.maps.DirectionsService();
//    const directionsRenderer = new google.maps.DirectionsRenderer();
//
//    // Set up map from view to focus on Saskatoon coordinates
//    var saskatoon = new google.maps.LatLng(52.118, -106.643)
//    map = new google.maps.Map(document.getElementById("route"), {
//        center: saskatoon,
//        zoom: 10,
//    })
//    // Set directions to be rendered to display map
//    directionsRenderer.setMap(map);
//
//    // Hook javascript to GUI button - render directions upon submission
//    var submitButton = document.getElementById("submitStart");
//    submitButton.addEventListener("click", test); /*<- change here to submit directions*/
//
//    }
//
//function calculateAndDisplayRoute(directionsService, directionsRenderer) {
//  directionsService.route(
//    {
//      origin: {
//        query: document.getElementById("text").value,
//      },
//      destination: {
//        query: document.getElementById("end").value,
//      },
//      travelMode: google.maps.TravelMode.DRIVING,
//    },
//    (response, status) => {
//      if (status === "OK") {
//        directionsRenderer.setDirections(response);
//      } else {
//        window.alert("Directions request failed due to " + status);
//      }
//    }
//  );
//}
//
//function test(){
//alert(document.getElementById("sCoords").value)
//}
////document.head.appendChild(routeScript);
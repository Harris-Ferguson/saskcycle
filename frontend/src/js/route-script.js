// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=places&v=weekly';
script.async = true;

var map;

window.markers = [];

/*
Usage for Directions API Code given by Google at:
https://developers.google.com/maps/documentation/javascript/examples/directions-simple#maps_directions_simple-javascript
*/

// Attach your callback function to the `window` object
window.initMap = function(){

        const directionsService = new google.maps.DirectionsService();
        const directionsRenderer = new google.maps.DirectionsRenderer();

        var saskatoon = new google.maps.LatLng(52.118, -106.643)

        map = new google.maps.Map(document.getElementById("route"), {
            center: saskatoon,
            zoom: 10,
        })

        directionsRenderer.setMap(map);

          const onChangeHandler = function () {
            calculateAndDisplayRoute(directionsService, directionsRenderer);
          };
          document.getElementById("submitStart").addEventListener("change", onChangeHandler);
          document.getElementById("end").addEventListener("change", onChangeHandler);
        }

function calculateAndDisplayRoute(directionsService, directionsRenderer) {
  directionsService.route(
    {
      origin: {
        query: document.getElementById("text").value,
      },
      destination: {
        query: document.getElementById("end").value,
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
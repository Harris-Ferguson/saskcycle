// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBBlDTjB1qNQkH_h7fVb4W9XNeEjfvJLEU&callback=initMap&libraries=&v=weekly';
script.async = true;

// Attach your callback function to the `window` object
window.initMap = function() {
    let map;

    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 52.1187838, lng: -106.634 },
        zoom: 12,
    });
};

// Append the 'script' element to 'head'
document.head.appendChild(script);
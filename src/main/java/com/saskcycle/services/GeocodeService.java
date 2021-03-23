package com.saskcycle.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.json.*;
import org.springframework.stereotype.Service;

@Service
public class GeocodeService implements Serializable {

    /* --------- Attributes ------------ */
    // JSON object containing latitude/longitude
    String baseUrl = "http://geogratis.gc.ca/services/geolocation/en/locate?q=";
    private JSONObject response;
    private double lat;
    private double lon;

        /* ----------- Methods ------------- */

    public GeocodeService(){}

    /**
     * Gets the geolocation for a given postal code
     * Sets the lat and lon fields in this object
     * @param postalCode valid canadian postal code
     */
    public void geolocationFromPostalCode(String postalCode) throws JSONException {
        postalCode = URLEncoder.encode(postalCode.trim().toLowerCase(Locale.ROOT), StandardCharsets.UTF_8);
        URL request;
        JSONArray array = new JSONArray();
        try {
            // Connects to geocoder service
            request = new URL(baseUrl + postalCode);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            array = new JSONArray(content.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray coords = array.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");
        setLat(Double.parseDouble(coords.get(0).toString()));
        setLon(Double.parseDouble(coords.get(1).toString()));
    }

    /**
     * Finds the distance from the point defined in the GeocodeService and the given point
     * @param otherLat other latitude
     * @param otherLon other longitude
     * @return distance value
     */
    public double distance(double otherLat, double otherLon){
        double latlon = (Math.pow(lat, 2) + Math.pow(lon, 2));
        double otherLatLon = (Math.pow(otherLat, 2) + Math.pow(otherLon, 2));
        double sum = latlon + otherLatLon;
        return Math.sqrt(sum);
    }

    public double getLat() {
        return lat;
    }

    private void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    private void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Test
     * @param args not used
     */
    public static void main(String[] args) {
        GeocodeService ser = new GeocodeService();
        try {
            ser.geolocationFromPostalCode("S7H2T2");
            System.out.println(ser.lat + " " + ser.lon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

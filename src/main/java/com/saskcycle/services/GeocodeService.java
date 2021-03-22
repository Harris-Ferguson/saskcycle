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
    String baseUrl = "https://geocoder.ca/?locate=";
    String urlSuffix = "&geoit=XML&json=1";
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
    public void geolocationFromPostalCode(String postalCode) {
        postalCode = URLEncoder.encode(postalCode.trim().toLowerCase(Locale.ROOT), StandardCharsets.UTF_8);
        URL request;
        getResponse(baseUrl + postalCode + urlSuffix);
        setLat(Double.parseDouble(response.getString("latt")));
        setLon(Double.parseDouble(response.getString("longt")));
    }

    public void geolocationFromStreetAddress(String streetAddress){
        String encodedStreetAddress = URLEncoder.encode(streetAddress + " saskatoon saskatchewan", StandardCharsets.UTF_8);
        getResponse(baseUrl + encodedStreetAddress + urlSuffix);
        setLat(Double.parseDouble(response.getString("latt")));
        setLon(Double.parseDouble(response.getString("longt")));
    }

    /**
     * Sets the response instance with the result of the http request to the given URL
     * @param url url to request
     */
    private void getResponse(String url) {
        URL request;
        try {
            // Connects to geocoder service
            request = new URL(url);
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
            response = new JSONObject(content.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

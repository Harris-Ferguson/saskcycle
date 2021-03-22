package com.saskcycle.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.json.*;
import org.springframework.stereotype.Service;

@Service
public class GeocodeService implements Serializable {

    /* --------- Attributes ------------ */
    // URL that returns JSON with latitude/longitude of an input postal code (Canada)
    private String urlBase = "https://geocoder.ca/";
    // 6 character postal code
    private String postalCode;
    // JSON object containing latitude/longitude
    private JSONObject response;
    private double lat;
    private double lon;

        /* ----------- Methods ------------- */

    public GeocodeService(){}

    public void findLatLon(String postalCode) {
        this.postalCode = postalCode.trim().toLowerCase(Locale.ROOT);
        URL request;
        try {
            // Connects to geocoder service
            request = new URL(urlBase + postalCode + "?json=1");
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
            setLat(Double.parseDouble((response.getString("latt"))));
            setLon(Double.parseDouble((response.getString("longt"))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}

package com.price_tracker.client.objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Requests {
  public static String sendLoginRequest(String url, String requestBody) throws IOException {
    URL requestUrl = new URL(url);
    HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setDoOutput(true);

    OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
    out.write(requestBody);
    out.flush();
    out.close();

    int responseCode = con.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      in.close();
      return response.toString();
    }  else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
      return "0";
    } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
      return "-1";
    } else {
      throw new IOException("Request failed with response code " + responseCode);
    }
  }

  public static String sendRegisterRequest(String url, String requestBody) throws IOException {
    URL requestUrl = new URL(url);
    HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setDoOutput(true);

    OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
    out.write(requestBody);
    out.flush();
    out.close();

    int responseCode = con.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_CREATED) {
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      in.close();
      return response.toString();
    } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
      // User already exists, print the response body
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      in.close();
      return response.toString();
    } else {
      throw new IOException("Request failed with response code " + responseCode);
    }
  }

  public static void sendAddProductRequest(String email, String name, String url, String image) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
    map.add("email", email);
    map.add("name", name);
    map.add("url", url);
    map.add("image", image);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/watchlists/add-product", request, String.class);

    System.out.println("Status code: " + response.getStatusCode());
    System.out.println("Response body: " + response.getBody());
  }
}

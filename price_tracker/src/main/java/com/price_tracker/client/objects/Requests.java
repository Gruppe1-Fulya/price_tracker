package com.price_tracker.client.objects;

import java.io.*;
import java.net.*;
import org.springframework.http.*;
import java.nio.charset.StandardCharsets;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

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
    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
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

  public static String sendAddProductRequest(String email, String name, String url, String image) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("email", email);
    map.add("name", name);
    map.add("url", url);
    map.add("image", image);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/watchlists/add-product", request, String.class);
    return response.toString();
  }

  public static String sendLoadWLRequest(int id) {
    try {
      URL url = new URL("http://localhost:8080/watchlists/load/" + id);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      int status = con.getResponseCode();
      if (status == 404) return "empty";

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      return response.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void sendDeleteProductRequest(int wid) {
    String url = "http://localhost:8080/watchlists/remove/" + wid;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
  }

  public static String sendLoadPricesRequest(int id) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    URI url = URI.create("http://localhost:8080/price/price-list/" + id);
    HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
    String response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
    if (response.equals("[]")) {
      return "empty";
    }
    return response;
  }

  public static String sendLoadAlarmRequest(int id) {
    String url = "http://localhost:8080/alarm/load-alarm/" + id;
    try {
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(
            con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        return response.toString();
      } else {
        return "Error: " + responseCode;
      }
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  public static String sendSetAlarmRequest(int id, int watchlist_id, String date_created, String condition, double target_price) {
    // create the request body string
    String requestBody = "{"
        + "\"productId\":" + id + ","
        + "\"watchlist_id\":" + watchlist_id + ","
        + "\"date_created\":\"" + date_created + "\","
        + "\"condition\":\"" + condition + "\","
        + "\"target_price\":" + target_price
        + "}";

    // create a new URL object and open a connection
    URL url;
    HttpURLConnection connection;
    try {
      url = new URL("http://localhost:8080/watchlists/set-alarm");
      connection = (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    // set the request method, headers, and body
    try {
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      OutputStream outputStream = connection.getOutputStream();
      outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    //// retrieve the response status code and body
    int responseCode;
    String responseBody;
    try {
      responseCode = connection.getResponseCode();
      InputStream inputStream = responseCode < HttpURLConnection.HTTP_BAD_REQUEST ? connection.getInputStream() : connection.getErrorStream();
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      responseBody = result.toString(StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    // close the connection
    connection.disconnect();

    // return the response body
    return responseBody;
  }

  public static String sendRemoveAlarmRequest(int wid) {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/watchlists/remove-alarm/" + wid;
    ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
    return response.getBody();
  }

  public static void sendUpdatePriceRequest(int pid) {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/price/update-price/" + pid;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
  }
}

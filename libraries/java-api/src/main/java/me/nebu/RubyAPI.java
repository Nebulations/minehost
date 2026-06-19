package me.nebu;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nebu.persistence.ServerInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class RubyAPI {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static String endpoint;

    public static void configure(String url) {
        RubyAPI.endpoint = url;
    }

    public static ServerInfo getServerFromId(String id) {
        Map<String, Object> data = sendRequest(endpoint + "/api/servers/server/" + id);
        if (data == null) return null;

        return new ServerInfo(
                (String) data.get("id"),
                (String) data.get("name"),
                (String) data.get("state"),
                (String) data.get("address"),
                ((Number) data.get("port")).intValue()
            );
    }

    public static ServerInfo getServerFromName(String name) {
        Map<String, Object> data = sendRequest(endpoint + "/api/servers/server/" + name + "?byName=true");
        if (data == null) return null;

        return new ServerInfo(
                (String) data.get("id"),
                (String) data.get("name"),
                (String) data.get("state"),
                (String) data.get("address"),
                ((Number) data.get("port")).intValue()
        );
    }

    private static Map<String, Object> sendRequest(String request) {
        try {
            HttpResponse<String> res = client.send(
                    HttpRequest.newBuilder(URI.create(request))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(res.body(), Map.class);

            if (!json.get("status").equals("OK")) {
                return null;
            }

            return mapper.convertValue(json.get("response"), Map.class);
        } catch (Exception e) {
            return null;
        }
    }

}

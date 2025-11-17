package gateways;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * HTTP gateway for making API calls
 * uses API key from the .env file
 * facade pattern (ApiKeyLoader, etc. are used to hide the complexity of the API calls)
 */
public class JavaHttpGateway {
    private ApiKeyLoader apiKeyLoader;
    private final String apiKey;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    
    public JavaHttpGateway() {
        this.apiKeyLoader = new ApiKeyLoader();
        this.apiKey = apiKeyLoader.loadApiKey();  // Delegation
    }
    
    /**
     * send a GET request to the URL
     */
    public String get(String url) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .GET();
        
        if (apiKey != null && !apiKey.isEmpty()) {
            builder.header("X-API-Key", apiKey);
        }
        
        HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new Exception("API request failed: " + response.statusCode() + " - " + response.body());
        }
    }
}

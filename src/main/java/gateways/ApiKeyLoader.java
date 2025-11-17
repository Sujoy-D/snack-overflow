package gateways;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * responsible for loading the API key from .env file or system environment
 */
public class ApiKeyLoader {
    private static Dotenv dotenv;
    
    static {
        try {
            dotenv = Dotenv.configure()
                    .directory(".")
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            dotenv = null;
        }
    }
    
    /**
     * load the API key from .env file
     * 
     * @return The API key, or null if not found
     */
    public String loadApiKey() {
        String key = null;
        if (dotenv != null) {
            key = dotenv.get("API_KEY");
        }
        if (key == null || key.isEmpty() || key.equals("your_api_key_here")) {
            key = System.getenv("API_KEY");
        }
        return key;
    }
}


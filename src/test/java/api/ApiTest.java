package api;


import gateways.JavaHttpGateway;

/**
 * test to see if the API works
 */
public class ApiTest {
    
    public static void main(String[] args) {
        try {
            JavaHttpGateway gateway = new JavaHttpGateway();
            
            // this is getting 2 recipes with apples, flour, and sugar
            String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=apples,flour,sugar&number=2";
            
            System.out.println("Calling: " + url);
            String response = gateway.get(url);
            
            System.out.println("\nResponse:");
            System.out.println(response);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

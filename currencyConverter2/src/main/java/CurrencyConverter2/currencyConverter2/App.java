package CurrencyConverter2.currencyConverter2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class App 
{
    public static void main( String[] args ) throws IOException, ParseException
    {
    	HashMap<Integer, String> currency_codes = new HashMap<Integer, String>();
    	
    	// Add currency codes
    	currency_codes.put(1, "USD");
    	currency_codes.put(2, "EUR");
    	currency_codes.put(3, "GBP");
    	currency_codes.put(4, "CHF");
    	currency_codes.put(5, "JPY");
    	
    	int from, to;
    	String from_code, to_code;
    	double amount;
    	
    	 Scanner scn = new Scanner(System.in);
    	 
    	 // Begin.
    	 System.out.println("Welcome to the Currency converter");
    	 
    	 System.out.println("Which currency do you wish to convert from?");
    	 
    	 System.out.println("1: USD (US Dollar) \t 2: EUR (Euro)"
    	 		+ " 3: GBP (British Pound) \t 4: CHF (Chinese Yuan Renminbi) \t"
    	 		+ " 5: JPY (Japanese Yen)");
    	 
    	 System.out.print("Enter digit here: ");
    	 from = scn.nextInt();
    	 while(from < 1 || from > 5) {
    		 System.out.println("Please enter a valid currency (1-5)");
    		 System.out.println("Which currency do you wish to convert from?");
    		 System.out.println("1: USD (US Dollar) \t 2: EUR (Euro)"
    	    	 		+ " 3: GBP (British Pound) \t 4: CHF (Chinese Yuan Renminbi) \t"
    	    	 		+ " 5: JPY (Japanese Yen)");
    		 System.out.print("Enter digit here: ");
    		 from = scn.nextInt();
    	 }
    	 
    	 from_code = currency_codes.get(from);
    	 
    	 System.out.println("You have chosen: " + from_code);
    	 
    	 System.out.println("Which currency do you wish to convert to?");
    	 
    	 System.out.println("1: USD (US Dollar) \t 2: EUR (Euro)"
    		 		+ " 3: GBP (British Pound) \t 4: CHF (Chinese Yuan Renminbi) \t"
    		 		+ " 5: JPY (Japanese Yen)");
    	 
    	 System.out.print("Enter digit here: ");
    	 
    	 to = scn.nextInt();
    	 while(to < 1 || to > 5) {
    		 System.out.println("Please enter a valid currency (1-5)");
    		 System.out.println("Which currency do you wish to convert to?");
    		 System.out.println("1: USD (US Dollar) \t 2: EUR (Euro)"
    	    	 		+ " 3: GBP (British Pound) \t 4: CHF (Chinese Yuan Renminbi) \t"
    	    	 		+ " 5: JPY (Japanese Yen)");
    		 System.out.print("Enter digit here: ");
    	to = scn.nextInt();
    	 }
    	 
    	 to_code = currency_codes.get(to);
    	 
    	 System.out.println("You have chosen: " + to_code);
    	 
    	 System.out.println("How much do you wish to convert?");
    	 System.out.print("Type in amount here: ");
    	 amount = (double)scn.nextFloat();
    	 
    	 
    	 double result = (double)sendHttpGETRequest(from_code, to_code, amount);
    	 
    	 System.out.println("That will be " + result + " " + to_code);
    	 
    	 System.out.println("Thank you for using the currency converter.");
    	 
    }
    
    private static double sendHttpGETRequest(String from_code, String to_code, Double amount) throws IOException, ParseException {
    	String GET_URL = "https://v6.exchangerate-api.com/v6/e56d6d73549d4a1b83e18f66/pair/" + from_code + "/"
    			+ to_code + "/" + amount.toString();
    	URL url = new URL(GET_URL);
    	HttpURLConnection HttpURLConnection = (HttpURLConnection) url.openConnection();
    	HttpURLConnection.setRequestMethod("GET");
    	int response_code = HttpURLConnection.getResponseCode();
    	JSONObject obj = null;
    	
    	if(response_code == HttpURLConnection.HTTP_OK) {
    		// success
    		BufferedReader in = new BufferedReader(new InputStreamReader(HttpURLConnection.getInputStream()));
    		String input_line;
    		StringBuffer response = new StringBuffer();
    		
    		while((input_line = in.readLine()) != null) {
    			response.append(input_line);
    		} in.close();
    		
    		JSONParser parser = new JSONParser();
    		obj = (JSONObject)parser.parse(response.toString());
    	} else {
    		// failure
    		System.out.println("GET request failed!");
    		System.out.println(response_code);
    	}
    	double result = (double)obj.get("conversion_result");
    	return result;
	}
}


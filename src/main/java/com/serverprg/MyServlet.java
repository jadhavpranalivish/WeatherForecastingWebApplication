package com.serverprg;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


 // Servlet implementation class MyServlet
//  @WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	 // HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		    doPost(request, response);

		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		// Forward to JSP when someone opens /MyServlet directly

	}

	
	 //HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String inputData = request.getParameter("userInput");
		//System.out.println(inputData);
		
		//API Setup
		String apiKey = "fa97330d2b3e7abfe94f32e00eb5dc12";
		//Get the city from the form input
		String city = request.getParameter("city");
		
		//Create the URL for the OpenWeatherMap API request
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apiKey;
		
		try
		{
			URL url = new URL(apiUrl);//This object represents the web address where you want to send a request.
			
			//Opens a connection to that URL.
            //openConnection() returns a general URLConnection object, but since this is HTTP (not FTP, file, etc.), we cast it to HttpURLConnection.
            //Now you can set HTTP-specific features like method (GET, POST),
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			//Tells the server that you want to use the GET method.
            //GET = request data from the server (most common method).
			connection.setRequestMethod("GET");
			
//			int responseCode = connection.getResponseCode();
//			if(responseCode == 200) {
//				//success: read InputStream
//			}
//			else{
//				request.setAttribute("errorMessage", "Unable to fetch weather data. Please check the city name.");
//			    request.getRequestDispatcher("index.jsp").forward(request, response);
//			    return;
//			};
			
		//  Reading the data from network
//			getInputStream() → opens a stream of bytes from server response.
//			InputStreamReader → converts those bytes into characters so you can actually read them as text (JSON, HTML, XML, etc.).
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
		//  Want to store in string
			StringBuilder responseContent = new StringBuilder();
			
		 // Input take from the reader, will create scanner object.
//			Scanner makes it easier to read text line by line or token by token.
//			Here, it will read the server’s response line by line.
			Scanner scanner = new Scanner(reader);
			
	//		while(scanner.hasNext())
//			Checks if there’s still more data (more lines/tokens) to read from the response.
//			Keeps looping until the whole response is consumed.
			while(scanner.hasNext())
			{
//				Reads the next line from the response.
//				Appends it to responseContent.
//				Over multiple iterations, it builds the complete server response into one string.
				responseContent.append(scanner.nextLine());
			}
			
//			Scanner reads the API response line by line.
//			Each line is added to StringBuilder.
//			Finally, you have the full response text stored in responseContent (usually JSON or XML).
			scanner.close();
			
		//  TypeCasting = parsing the data into JSON
			Gson gson = new Gson();
//			responseContent is a StringBuilder in your code.
//			It stores the full JSON response that came from the OpenWeather API.
//			.toString() converts the StringBuilder into a plain String.
//			takes the raw JSON text from the API and converts it into a Java JsonObject,
//			so you can extract values like temperature, humidity, weather condition, etc.
			JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
			
		   // System.out.println(jsonObject);
		
		    //Date & Time
//		    jsonObject.get("dt").getAsLong()
//
//		    In OpenWeatherMap (and similar APIs), "dt" is the timestamp of the weather data.
//
//		    Example JSON:
//
//		    {
//		      "dt": 1694502300
//		    }
//
//
//		    This value is in Unix time (seconds since 1970-01-01 UTC).
//
//		    So: 1694502300 = seconds.
//
//		    * 1000
//
//		    Java’s Date class works with milliseconds, not seconds.
//
//		    Multiply by 1000 → convert seconds to milliseconds.
//
//		    Example: 1694502300 * 1000 = 1694502300000L
//
//		    new Date(dateTimeStamp)
//
//		    Creates a Java Date object from the timestamp.
//
//		    Example:
//
//		    new Date(1694502300000L)
//		    → "Sat Sep 12 10:45:00 IST 2025"
//
//
//		    .toString()
//
//		    Converts the Date object to a human-readable string (system default format).
		    long dateTimeStamp = jsonObject.get("dt").getAsLong() * 1000;
		    String date = new Date(dateTimeStamp).toString();
		    
		    
		    //Temperature
		   //The OpenWeatherMap API gives temperature in Kelvin by default.
		    double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
		    int temperatureCelsius = (int)(temperatureKelvin - 273.15);//Convert Kelvin → Celsius
		    
		    
		    //Humidity
		    int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
		    
		    //Wind speed
		    double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
		    
		    //Weather condition
		    String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
		    
		    // Set the data as request attributes (for sending to the jsp page)
		    //request.setAttribute()?
           //It stores data (key–value pairs) inside the request object.
           //These values are available only for the current request (not across sessions).
           //Useful when you forward the request from a Servlet → JSP.
		   // e.g
		    //request.setAttribute("city", city);
		    //→ Stores the city name you searched (e.g., "Mumbai").
		    request.setAttribute("date", date);
		    request.setAttribute("city", city);
		    request.setAttribute("temperature", temperatureCelsius);
		    request.setAttribute("weatherCondition", weatherCondition);
		    request.setAttribute("humidity", humidity);
		    request.setAttribute("windSpeed", windSpeed);
		    request.setAttribute("weatherData", responseContent.toString());
		
		    
		    connection.disconnect();
		    
		    //Forward the request to the index.jsp page for rendering.
//		    RequestDispatcher is an interface in the Servlet API (jakarta.servlet or javax.servlet depending on version).
//		    It is used to forward or include requests from one resource (Servlet, JSP, HTML) to another on the server side.
		    request.getRequestDispatcher("index.jsp").forward(request, response);
		    
		}
		catch(IOException e)
		{
			request.setAttribute("errorMessage", "Something went wrong: " + e.getMessage());
		    request.getRequestDispatcher("index.jsp").forward(request, response);
		    return;
			
		}
		
		
	}

}

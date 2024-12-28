//Requirements
//API Key: Sign up at OpenWeatherMap and get your API key.
//Dependencies: Use a library like OkHttp for making HTTP requests and Gson for parsing JSON.
//Add these dependencies to your build.gradle.kts file
//implementation("com.squareup.okhttp3:okhttp:4.11.0")
//implementation("com.google.code.gson:gson:2.10.1")

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

// Data classes to parse the JSON response
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)

fun main() {
    val apiKey = "YOUR_API_KEY" // Replace with your OpenWeatherMap API key
    val city = "London" // Change this to the city you want to predict weather for
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$apiKey"

    // Create an HTTP client
    val client = OkHttpClient()

    // Build the request
    val request = Request.Builder()
        .url(url)
        .build()

    // Execute the request
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            println("Failed to fetch weather data: ${response.message}")
            return
        }

        // Parse the JSON response
        val responseBody = response.body?.string()
        if (responseBody != null) {
            val weatherResponse = Gson().fromJson(responseBody, WeatherResponse::class.java)

            // Display the weather data
            println("City: $city")
            println("Temperature: ${weatherResponse.main.temp}°C")
            println("Humidity: ${weatherResponse.main.humidity}%")
            println("Description: ${weatherResponse.weather[0].description}")
        } else {
            println("No response from server")
        }
    }
}

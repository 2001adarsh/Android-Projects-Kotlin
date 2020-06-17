package com.adarsh.weatherapi.weather

data class WeatherResponse(
	val request: Request? = null,
	val current: Current? = null,
	val location: Location? = null
)

data class Request(
	val unit: String? = null,
	val query: String? = null,
	val language: String? = null,
	val type: String? = null
)

data class Current(
	val weatherDescriptions: List<String?>? = null,
	val observationTime: String? = null,
	val windDegree: Int? = null,
	val visibility: Int? = null,
	val weatherIcons: List<String?>? = null,
	val feelslike: Int? = null,
	val isDay: String? = null,
	val windDir: String? = null,
	val pressure: Int? = null,
	val cloudcover: Int? = null,
	val precip: Int? = null,
	val uvIndex: Int? = null,
	val temperature: Int? = null,
	val humidity: Int? = null,
	val windSpeed: Int? = null,
	val weatherCode: Int? = null
)

data class Location(
	val localtime: String? = null,
	val utcOffset: String? = null,
	val country: String? = null,
	val localtimeEpoch: Int? = null,
	val name: String? = null,
	val timezoneId: String? = null,
	val lon: String? = null,
	val region: String? = null,
	val lat: String? = null
)


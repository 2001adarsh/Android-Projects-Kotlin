#### Zoom Level:
The zoom level controls how zoomed in you are on the map. The following list gives you an idea of what level of detail each level of zoom shows:

 1: World

5: Landmass/continent

10: City

15: Streets

20: Buildings


#### Now we can run a Directions API request that looks like the form below:
https://maps.googleapis.com/maps/api/directions/json?origin=ADDRESS_1&destination=ADDRESS_2&waypoints=ADDRESS_X|ADDRESS_Y&key=API_KEY

· Origin — the address, textual latitude/longitude value, or place ID from which you wish to calculate directions.

· Destination — the address, textual latitude/longitude value, or place ID to which you wish to calculate directions. The options for the destination parameter are the same as for the origin parameter, described above.

· Waypoints — specifies an array of waypoints. Waypoints alter a route by routing it through the specified location(s). A waypoint is specified as a latitude/longitude coordinate, an encoded polyline, a place ID, or an address which will be geocoded.

· Key — your application’s API key. This key identifies your application for purposes of quota management.

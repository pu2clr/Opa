

var gps = {};

gps.defaultLocation = {
    "accuracy": 0,
    "altitude": 0,
    "altitudeAccuracy": null,
    "heading": 0,
    "latitude": 0,
    "longitude": 0,
    // "latitude": -15.761189373507607,
    // "longitude": -47.88114954038471,    
    "speed": 0,
    "timestamp": 0
};


Ti.Geolocation.purpose = 'Informar a sua localização ao provedor do serviço';
// Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_BEST;
Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_HIGH;
// Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_LOW;  
Ti.Geolocation.distanceFilter = 10;
Ti.Geolocation.preferredProvider = Ti.Geolocation.PROVIDER_GPS;

gps.coords = gps.defaultLocation;
getCurrentLocation();

/*
 * Check GPS
 */

function checkGPS() {
	return (Ti.Geolocation.locationServicesEnabled);
}


/*
 * Get Location
 * 
 */
function getCurrentLocation() {

	if (Ti.Geolocation.locationServicesEnabled) {
	    Titanium.Geolocation.getCurrentPosition(function(e) {
			if (e.error)
			{
 				ShowMessage("Não foi possível obter sua localização.")
           		gps.coords = gps.defaultLocation;
				return;
			}
 			gps.coords = e.coords;
	    });
	} else {
	    alert('Habilite o serviço de localização do seu dispositivo.');
	}
}

 
Titanium.Geolocation.addEventListener('location',function(e)
{
	if (e.error)
		{
			ShowMessage("Não foi possível obter sua localização.")
            gps.coords = gps.defaultLocation;
			return;
		}
 
 		gps.coords = e.coords;
});




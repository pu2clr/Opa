

Ti.include('includes/opa.js');

var device_registration;	// = Ti.UI.createWindow({backgroundColor:'black'});
var device_activation;	    // = Ti.UI.createWindow({backgroundColor:'black'});
var services_list;		    // = Ti.UI.createWindow({backgroundColor:'black'});
var service_request         // = Ti.UI.createWindow({backgroundColor:'black'});


/*
 * Check if device is registred
 */

var json;
var xhr1 = Ti.Network.createHTTPClient( {timeout:35000} );

xhr1.onload = function() {

	json = JSON.parse(this.responseText);
	opa.deviceStatus = json.result;

	if ( opa.deviceStatus == "Activated" ) {
		services_list = Ti.UI.createWindow({title:'Lista de Serviços', backgroundColor:'black', url: 'mainApp/services_list.js'});
		services_list.open();
	} else if ( opa.deviceStatus == "Device not found") {
		device_resgistration = Ti.UI.createWindow({title:'Registro do Dispositivo', backgroundColor:'black', url: 'mainApp/device_registration.js'});
		device_resgistration.open();	
	} else if ( opa.deviceStatus == "Pending") {
		device_activation = Ti.UI.createWindow({title:'Ativação do Dispositivo', backgroundColor:'black', url: 'mainApp/device_activation.js' });
		device_activation.open();
	}
	
}

xhr1.onerror = function(e) {
	ShowMessage(opa.NETWORK_ERROR);
}

xhr1.open("POST", opa.deviceStatusURL + getDeviceId());
xhr1.send();

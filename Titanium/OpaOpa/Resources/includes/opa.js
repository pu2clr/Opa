
var opa = {};

opa.NETWORK_ERROR = "Houve um erro ao tentar acessar o servidor. Verifique sua conexão WI-FI ou 3G e tente novamente.";


opa.baseURL         = "http://www.consultoriaavaliar.com.br/OpsAdmin/public/oparest";
opa.serviceURL      = opa.baseURL + "/topservices";
opa.deviceStatusURL = opa.baseURL + "/statusdevice/deviceId/";
opa.imagesURL       = "http://www.consultoriaavaliar.com.br/OpsAdmin/public/images/";


opa.deviceStatus = 'Desconhecido';


function ShowMessage(msgTitle, msgText) {
	 var alertDialog = Titanium.UI.createAlertDialog({
         title: msgTitle,
         message: msgText,
         buttonNames: ['OK']  });
         alertDialog.show();
}


/*
 * Get Device ID (UDID)
 */
function getDeviceId() {
	if ( Titanium.Platform.id == null || Titanium.Platform.id == '' || Titanium.Platform.id == ' ') {
		// return '0000A0000B0000C0000D';
		return '000000000000000';
	} 
	return Titanium.Platform.id;
}

function getUserName() {
	return Titanium.Platform.getUsername();
}

function getDeviceName() {
	return Titanium.Platform.getName() + "-" +  Titanium.Platform.getModel() + "-" + Titanium.Platform.getManufacturer();
}


function getOSVersion() {
	return Titanium.Platform.osname + "-" + Titanium.Platform.version;
}



function ShowMessage(msgTitle, msgText) {
	 var alertDialog = Titanium.UI.createAlertDialog({
         title: msgTitle,
         message: msgText,
         buttonNames: ['OK']  });
         alertDialog.show();
}




/*
* Check Network Connection
*/

function checkNetwork() {
	return (Titanium.Network.online);
}



/*
 * Check Camera
 */
function checkCamera() {
	return (Ti.Media.isCameraSupported);
}


/*
 * Check Opa Service is Available
 */
function checkOpaService() {

	return "OK";
}




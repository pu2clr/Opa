
var win	= Ti.UI.currentWindow;

var scrollView	= Ti.UI.createScrollView(
  {backgroundColor: 'black',
  exitOnClose: true,
  fullscreen: true,
  title: 'Registro do Dispositivo',
  showVerticalScrollIndicator: true,
  height: '65%',
  width: '100%',
  top:5 }   	
);


Ti.include('../includes/opa.js');


var cancel = Titanium.UI.createButton({
	    systemButton : Titanium.UI.iPhone.SystemButton.CANCEL
});
	
var userNameField = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'Nome completo',
    keyboardType : Titanium.UI.KEYBOARD_ASCII,
    keyboardToolbar : [cancel],
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: { fontSize:16},
    top : 10,
    left: 0,
    width : '100%', height : 40
});	

var userEmailField = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'email',
    keyboardToolbar : [cancel],
    keyboardType:Titanium.UI.KEYBOARD_EMAIL,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: { fontSize:16},  
    top : 60,
	left: 0,
    width : '100%', height : 40
});	


var userDDD1Field = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'DDD',
    keyboardType:Titanium.UI.KEYBOARD_NUMBER_PAD,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: {fontSize:16},   
    top : 110,
	left: 0,
    width : 50, height : 40
});	


var userPhone1Field = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'Número deste telefone',
    keyboardType:Titanium.UI.KEYBOARD_NUMBER_PAD,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: {fontSize:15},   
    top : 110,
	left: 55,
    width : 215, height : 40
});	


var userDDD2Field = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'DDD',
    keyboardType:Titanium.UI.KEYBOARD_NUMBER_PAD,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: {fontSize:16},   
    top : 160,
	left: 0,
    width : 50, height : 40
});	


var userPhone2Field = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'Confirme o número anterior',
    keyboardType:Titanium.UI.KEYBOARD_NUMBER_PAD,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    font: {fontSize:15},   
    top : 160,
	left: 55,
    width : 215, height : 40
});	


var sendButton = Titanium.UI.createButton({
   title: 'Enviar',
   top: 210,
   width: '100%',
   height: 40
});

var urlAux;

var device_activation	= Ti.UI.createWindow({backgroundColor:'black'});
device_activation.url = 'device_activation.js';
	
var json;
var xhr1 = Ti.Network.createHTTPClient( {timeout:35000} );
xhr1.onload = function() {

	json = JSON.parse(this.responseText);

	device_activation.open();	
}

xhr1.onerror = function(e) {

	ShowMessage('Houve um erro ao tentar acessar o servidor. Verifique sua conexão WI-FI ou 3G e tente novamente.');
}



	
sendButton.addEventListener('click',function(e)
{
	
		var telefone2 = userDDD2Field.getValue() + "-" + userPhone2Field.getValue();
		var telefone1 = userDDD1Field.getValue() + "-" + userPhone1Field.getValue();
	
		if ( telefone1 != telefone2 ) {
			ShowMessage("Os campos de DDD e Telefone devem ser os iguais. O número " + telefone1 + " difere de " + telefone2);
		} else {
	
			urlAux = opa.baseURL  + "/registerdevice/deviceId/" + Ti.Network.encodeURIComponent(getDeviceId()) + 
						"/serialNumber/" + "0000" + 
						"/osName/" +  Ti.Network.encodeURIComponent(getOSVersion()) +
						"/osVersion/" + Ti.Network.encodeURIComponent(getOSVersion()) +
			            "/deviceName/" + Ti.Network.encodeURIComponent(getDeviceName()) + 
			            "/userName/" + Ti.Network.encodeURIComponent(userNameField.getValue()) +
			            "/email/" + Ti.Network.encodeURIComponent(userEmailField.getValue()) + 
			            "/phoneDDD/" + userDDD1Field.getValue() +
			            "/phoneNumber/" + Ti.Network.encodeURIComponent(userPhone1Field.getValue());
			            	
			xhr1.open("POST", urlAux);
			xhr1.send();

		}

});


scrollView.add(userNameField);
scrollView.add(userEmailField);
scrollView.add(userDDD1Field);
scrollView.add(userPhone1Field);
scrollView.add(userDDD2Field);
scrollView.add(userPhone2Field);
scrollView.add(sendButton);
win.add(scrollView);

win.open();

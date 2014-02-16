var win	= Ti.UI.currentWindow;

Ti.include('../includes/opa.js');


var labelMessage = Ti.UI.createLabel({
  color: '#900',
  font: { fontSize:18 },
  text: 'Código de Ativação',
  textAlign: Ti.UI.TEXT_ALIGNMENT_LEFT,
  top: 10, 
// left: 10,
  width: '100%', height: 60
});


var codeField = Titanium.UI.createTextField({
    borderStyle : Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
    hintText : 'Informe o código de ativação',
    font: { fontSize:16 },
    keyboardType:Titanium.UI.KEYBOARD_NUMBER_PAD,
    keyboardToolbarColor : '#999',
    keyboardToolbarHeight : 40,
    top : 50,
//    left: 10,
    width : '100%', height : 60
});	

var sendButton = Titanium.UI.createButton({
   title: 'Enviar',
   top: 120,
   width: '100%',
   height: 60
});


var json;
var xhr1 = Ti.Network.createHTTPClient( {timeout:9000} );
xhr1.onload = function() {

	json = JSON.parse(this.responseText);
	Titanium.API.info('OnLoad===>' + this.responseText);
	Titanium.API.info('URL======>' + urlAux);
	if ( json.result == 'Activated') {
		services_list.open();
	} else {
		ShowMessage('O código de ativação informado não confere com o enviado para você. Verifique o seu SMS ou email e tente novamente.');
	}
	
}

xhr1.onerror = function(e) {
	Titanium.API.info(e.error);
	Titanium.API.info('URL====>' + urlAux );
	
	ShowMessage('Houve um erro ao tentar acessar o servidor. Verifique sua conexão WI-FI ou 3G e tente novamente.');
}

var urlAux;

var services_list	= Ti.UI.createWindow({backgroundColor:'black', url:'services_list.js'});
	
sendButton.addEventListener('click',function(e)
{
		var typedCode = codeField.getValue();

		if (  typedCode == '' || typedCode == ' ') {
			ShowMessage("Informe o código de ativação do dispositivo. Verifique seu email ou SMS.");
		} else {
			urlAux = opa.baseURL  + "/activedevice/deviceId/" + Ti.Network.encodeURIComponent(getDeviceId()) + 
									"/typedCode/"+ Ti.Network.encodeURIComponent(codeField.getValue());
			xhr1.open("POST", urlAux );
			xhr1.send();
		}
});



win.add( codeField );
win.add(sendButton);
win.open();


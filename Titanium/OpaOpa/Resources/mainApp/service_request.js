var win	= Ti.UI.currentWindow;

Ti.include('../includes/opa.js');
// Ti.include('../includes/gps.js');

var labelMessage = Ti.UI.createLabel({
  color: 'black',
  font: { fontSize:15 },
  text: 'Mensagem para ' + win.service_name,
  textAlign: Ti.UI.TEXT_ALIGNMENT_LEFT,
  top: 5, 
  left: 0,
  width: 'auto', height: 'auto'
});

var textAreaMessage = Ti.UI.createTextArea({
  borderWidth: 4,
  borderColor: 'black',
  borderRadius: 5,
  color: 'black',
  font: { fontSize:16 },
  font: {fontSize:20, fontWeight:'bold'},
  keyboardType: Ti.UI.KEYBOARD_ASCII,
  returnKeyType: Ti.UI.RETURNKEY_GO,
  textAlign: 'left',
  top: 30, 
  width: '100%', height : 80
});

var buttonSend = Titanium.UI.createButton({
   title: 'Enviar',
   top: 120,
   width: '45%',
   right: 0,
   height: 60
});

var buttonHome = Titanium.UI.createButton({
   title: 'Retornar',
   top: 120,
   width: '45%',
   left: 0,
   height: 60
});


var buttonZoomPlus = Titanium.UI.createButton({
   title: 'Zoom +',
   bottom: 10,
   width: '45%',
   left: 0,
   height: 60,
   opacity: 0.5
});

var buttonZoomMinus = Titanium.UI.createButton({
   title: 'Zoom -',
   bottom: 10,
   width: '45%',
   right: 0,
   height: 60,
   opacity: 0.5
});


var urlAux;

var device_activation	= Ti.UI.createWindow({backgroundColor:'black'});
device_activation.url = 'device_activation.js';

	
var json;
var xhr1 = Ti.Network.createHTTPClient( {timeout:35000} );
xhr1.onload = function() {

	json = JSON.parse(this.responseText);
	
	if ( json.result != "OK" ) {
		ShowMessage("Ocorreu um erro em sua solicitação. Tente novamente!");
	}
	else {
		win.remove(win.mapView);
		win.masterWindow.add(win.mapView);
		win.masterWindow.add(win.tableView);				
		win.close();
		ShowMessage("Solicitação enviada com sucesso!");
	}
	
}

xhr1.onerror = function(e) {

	ShowMessage('Houve um erro ao tentar acessar o servidor. Verifique sua conexão WI-FI ou 3G e tente novamente.');
}


buttonSend.addEventListener('click',function(e)
{

		if ( textAreaMessage.getValue() == "") {
			ShowMessage("Informe a mensagem a ser enviada para o prestador de serviço.");
		} else {
	
			urlAux = opa.baseURL  + "/requestservice/deviceId/" + Ti.Network.encodeURIComponent(getDeviceId()) + 
						"/latitude/" + win.coords.latitude + 
						"/longitude/" +  win.coords.longitude +
						"/serviceId/" + win.serviceId + // Atribuído em services_lists
			            "/message/" + Ti.Network.encodeURIComponent(textAreaMessage.getValue() ) + 
			            "/mediaType/" + "1" +
			            "/fileName/" +  "nofile";
			            	
			xhr1.open("POST", urlAux);
			xhr1.send();

		}

});


buttonHome.addEventListener('click',function(e)
{
	win.remove(win.mapView);
	win.masterWindow.add(win.mapView);
	win.masterWindow.add(win.tableView);
	win.close();
});


buttonZoomPlus.addEventListener('click',function(e)
{
	var latDelta  = win.mapView.latitudeDelta + 0.01; 
	var longDelta = win.mapView.latitudeDelta + 0.01;	
	
	win.mapView.setRegion({latitudeDelta: latDelta, longitudeDelta: longDelta });
	
});


buttonZoomMinus.addEventListener('click',function(e)
{
	var latDelta  = win.mapView.latitudeDelta - 0.01; 
	var longDelta = win.mapView.latitudeDelta - 0.01;	
	
	win.mapView.setRegion({latitudeDelta: latDelta, longitudeDelta: longDelta });
});



var scrollView	= Ti.UI.createScrollView(
  {backgroundColor: 'transparent',
  exitOnClose: true,
  fullscreen: true,
  title: 'Solicitação do Serviço',
  showVerticalScrollIndicator: true,
  focusable:true,
  height: '100%',
  width: '100%',
  top:0 }   	
);


scrollView.add(labelMessage);
scrollView.add(textAreaMessage);
scrollView.add(buttonSend);
scrollView.add(buttonHome);
scrollView.add(buttonZoomPlus);
scrollView.add(buttonZoomMinus);

win.add(win.mapView );

win.add(scrollView);

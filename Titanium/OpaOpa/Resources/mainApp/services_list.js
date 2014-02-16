var win	= Ti.UI.currentWindow;

Ti.include('../includes/opa.js');
Ti.include('../includes/gps.js');

var tableData = [];
var json, service_name, description, serviceNamenameLabel, serviceDescriptionLabel;


/* 
 *  Main Windows
 *  Shows the services avaiables
 */
var tableView = Ti.UI.createTableView({
  // backgroundColor:'white'
  backgroundColor:'transparent'
  // data:tableData
});



var mapview = Titanium.Map.createView({
	
		    mapType:  Titanium.Map.SATELLITE_TYPE, //Titanium.Map.HYBRID_TYPE,
		    region: {
	    		latitude:  gps.coords.latitude, 
	    		longitude: gps.coords.longitude,
		        latitudeDelta: 0.01, 
		        longitudeDelta: 0.01
		    },
		    opacity: 0.4,
		    animate:true,
		    regionFit:true,
		    userLocation:true,
		    top: 0
});


var xhr = Ti.Network.createHTTPClient( {timeout:35000} );

xhr.onload = function() {

	Titanium.API.info(this.responseText);
	
	json = JSON.parse(this.responseText);
	for (var i = 0; i < json.topservices.length; i++) {
	    service_name = json.topservices[i].service_name;
	    description = json.topservices[i].description;
	    
	    row = Ti.UI.createTableViewRow({
	        height:80
	    });

 		var imageAvatar = Ti.UI.createImageView({
   		 	image: opa.imagesURL +  json.topservices[i].icon_name,
    		left:10, top:5,
    		width:50, height:50
  		});
 		
  		row.add(imageAvatar);

	    serviceNameLabel = Ti.UI.createLabel({
	        text:service_name,
	        font:{
	            fontSize:18,
		    fontWeight:'bold'
		},
		height:'auto',
		left:70,
		top:5,
		color:'#000',
		touchEnabled:false
	    });
	    
	    serviceDescriptionLabel = Ti.UI.createLabel({
		text: description,
		font:{
		    fontSize:12
		},
		height:'auto',
		left:70,
		bottom:5,
		color:'#000',
		touchEnabled:false
	    });
 
	    row.add(serviceNameLabel);
	    row.add(serviceDescriptionLabel);
	    
	    row.hasChild = true;
	    
	    tableData.push(row);
      }
      
      tableView.setData(tableData);
}


xhr.onerror = function(e) {
		ShowMessage(opa.NETWORK_ERROR);
}


xhr.open("GET", opa.serviceURL);
xhr.send();

var service_request;

tableView.addEventListener('click', function(e){ 

	service_request = null;
	
	var service_request  = Ti.UI.createWindow({title:'Solicitação do Serviço', backgroundColor:'black', url: 'service_request.js'});

	service_request.serviceId    = json.topservices[e.index].service_id;
	service_request.service_name = json.topservices[e.index].service_name;
	service_request.description  = json.topservices[e.index].description;	
	service_request.coords = gps.coords;
	service_request.mapView = mapview;
	service_request.tableView = tableView;
	service_request.masterWindow = win;
	
	win.remove(mapview);
	win.remove(tableView);
	
	service_request.open();	 

});


win.add(mapview);



mapview.setRegion({latitude:  gps.coords.latitude, 
	    		     longitude: gps.coords.longitude,
	                 latitudeDelta: 0.01,
	                 longitudeDelta: 0.01 });


win.add(tableView);





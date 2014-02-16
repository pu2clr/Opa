<%-- 
    Document   : location
    Created on : Oct 6, 2011, 12:06:25 PM
    Author     : rcaratti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <style type="text/css">
            html { height: 100% }
            body { height: 100%; margin: 0; padding: 0 }
            #map_canvas { height: 100% }
        </style>
        <script type="text/javascript"
                src="http://maps.googleapis.com/maps/api/js?sensor=true&language=pt_BR">
        </script>
        <script type="text/javascript">
            var geocoder;
            var map;
            var infowindow = new google.maps.InfoWindow();
            var marker;
            
            function initialize() {
                geocoder = new google.maps.Geocoder();
                // var latlng = new google.maps.LatLng(-15.761336,-47.881031);
            <%
                    out.println("var latlng = new google.maps.LatLng(" + request.getParameter("latitude") + "," + request.getParameter("longitude") + ");");
            %>
                    var myOptions = {
                        zoom: 16,
                        center: latlng,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    }
                    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
                    marker = new google.maps.Marker({
                        position: latlng, 
                        map: map, 
                        title:"Local aproximado da solicitação"
                    });   
                }
            
                function detectBrowser() {
                    var useragent = navigator.userAgent;
                    var mapdiv = document.getElementById("map_canvas");
    
                    if (useragent.indexOf('iPhone') != -1 || useragent.indexOf('Android') != -1 ) {
                        mapdiv.style.width = '100%';
                        mapdiv.style.height = '100%';
                    } else {
                        mapdiv.style.width = '600px';
                        mapdiv.style.height = '800px';
                    }
                }           

                function codeLatLng() {
                    var input = document.getElementById("latlng").value;
                    var latlngStr = input.split(",",2);
                    var lat = parseFloat(latlngStr[0]);
                    var lng = parseFloat(latlngStr[1]);
                    var latlng = new google.maps.LatLng(lat, lng);
                    geocoder.geocode({'latLng': latlng}, function(results, status) {
                        if (status == google.maps.GeocoderStatus.OK) {
                            if (results[1]) {
                                map.setZoom(11);
                                marker = new google.maps.Marker({
                                    position: latlng,
                                    map: map
                                });
                                infowindow.setContent(results[1].formatted_address);
                                infowindow.open(map, marker);
                            }
                        } else {
                            alert("Geocoder failed due to: " + status);
                        }
                    });
                }        </script>
    </head>
    <body onload="initialize()">



        <div id="map_canvas" style="width:100%; height:100%"></div>





    </body>
</html>
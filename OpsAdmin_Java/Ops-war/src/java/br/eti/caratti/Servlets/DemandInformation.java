/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.Servlets;

import br.eti.caratti.ops.ejb.OpaEJB;
import br.eti.caratti.ops.entity.Device;
import br.eti.caratti.ops.entity.Occurrence;
import br.eti.caratti.ops.entity.ServiceProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rcaratti
 */
@WebServlet(name = "DemandInformation", urlPatterns = {"/DemandInformation"})
public class DemandInformation extends HttpServlet {

    @EJB
    OpaEJB opaEJB;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        StringBuilder strJavaScript = new StringBuilder(0);


        try {

            Integer Id = new Integer(request.getParameter("id"));
            Occurrence o = opaEJB.getOccurrence(Id);
            Device d = opaEJB.getDevice(o.getDeviceId());
            ServiceProvider sp = opaEJB.getServiceProvider(o.getServiceProviderId());

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Detalhamento da Demanda</title>");
            
            strJavaScript.append("\n<meta name='viewport' content='initial-scale=1.0, user-scalable=no' />");
            strJavaScript.append("\n<style type='text/css'>");
            strJavaScript.append("\nhtml { height: 100% }");
            strJavaScript.append("\nbody { height: 100%; margin: 0; padding: 0 }");
            strJavaScript.append("\n#map_canvas { height: 100% }");
            strJavaScript.append("\n</style>");
            strJavaScript.append("\n<script type='text/javascript' src='http://maps.googleapis.com/maps/api/js?sensor=true&language=pt_BR'> </script>");
            strJavaScript.append("\n<script type='text/javascript'>");
            strJavaScript.append("\nvar geocoder;");
            strJavaScript.append("\nvar map;");
            strJavaScript.append("\nvar infowindow = new google.maps.InfoWindow();");
            strJavaScript.append("\nvar marker;");
            strJavaScript.append("\nfunction initialize() {");
            strJavaScript.append("\ngeocoder = new google.maps.Geocoder();");
            strJavaScript.append("\nvar latlng = new google.maps.LatLng(" + o.getLatitude() + ", " + o.getLongitude() + ");");
            strJavaScript.append("\nvar myOptions = {");
            strJavaScript.append("\nzoom: 16, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP}");
            strJavaScript.append("\nmap = new google.maps.Map(document.getElementById('map_canvas'), myOptions);");
            strJavaScript.append("\nmarker = new google.maps.Marker({position: latlng, map: map, title:'Local aproximado da solicitação'});}");
            strJavaScript.append("\nfunction detectBrowser() { var useragent = navigator.userAgent; var mapdiv = document.getElementById('map_canvas');");
            strJavaScript.append("\nif (useragent.indexOf('iPhone') != -1 || useragent.indexOf('Android') != -1 ) {");
            strJavaScript.append("\nmapdiv.style.width = '100%'; mapdiv.style.height = '100%';} else { mapdiv.style.width = '600px';");
            strJavaScript.append("\nmapdiv.style.height = '800px';} }");
            strJavaScript.append("\nfunction codeLatLng() { var input = document.getElementById('latlng').value;");
            strJavaScript.append("\nvar latlngStr = input.split(',',2); var lat = parseFloat(latlngStr[0]);");
            strJavaScript.append("\nvar lng = parseFloat(latlngStr[1]); var latlng = new google.maps.LatLng(lat, lng);");
            strJavaScript.append("\ngeocoder.geocode({'latLng': latlng}, function(results, status) {");
            strJavaScript.append("\nif (status == google.maps.GeocoderStatus.OK) {");
            strJavaScript.append("\nif (results[1]) {map.setZoom(11); marker = new google.maps.Marker({position: latlng,map: map});");
            strJavaScript.append("\ninfowindow.setContent(results[1].formatted_address); infowindow.open(map, marker);}} else {");
            strJavaScript.append("\nalert('Geocoder failed due to: ' + status); }});}</script> ");
            
            out.println(strJavaScript.toString());
            
            
            
            out.println("</head>");
            
            out.println("<body onload='initialize()'>");

            out.println("<br>Há uma solicitação para " + sp.getName() + "<br>");
            out.println("<br>Hora do pedido: " + o.getDatetime().toString());            
            out.println("<br>Nome..........: " + d.getUserName());
            out.println("<br>Telefone......: " + d.getPhoneNumber());
            out.println("<br>Coordenadas...: (" + o.getLatitude() + ", " + o.getLongitude() + ")");            
            out.println("<br><B>Mensagem:</B>");
            out.println("<p><i>" + o.getMessage() + "</i></p>");

  

            out.println("<div id='map_canvas' style='width:100%; height:100%'></div>");

            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

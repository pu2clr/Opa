/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.ops.ejb;

import br.eti.caratti.ops.entity.ServiceProvider;
import br.eti.caratti.ops.entity.Device;
import br.eti.caratti.ops.entity.Occurrence;
import br.eti.caratti.ops.entity.Service;
import br.eti.caratti.ops.entity.TopSixService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/* SMS
 * Serviço de SMS da byJG
 */
import byjg.webservices.sms.*;
import com.rosaloves.bitlyj.*;
import static com.rosaloves.bitlyj.Bitly.*;


/**
 *
 * @author rcaratti
 */
@Stateless
@LocalBean
public class OpaEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method
    @PersistenceContext
    EntityManager manager;

    // Retorna os seis serviços mais utilizados
    public List<TopSixService> getTopSixServices() {

        return manager.createNamedQuery("TopSixService.findAll", TopSixService.class).getResultList();
    }

    // Retorna os serviços disponíveis por tipo
    public List<Service> getServices(String serviceType) {

        List<Service> s;
        try {
            s = manager.createNamedQuery("Service.findByServiceType", Service.class).setParameter("serviceType", serviceType).getResultList();
            return s;

        } catch (NoResultException e) {
            return null;
        }

    }

    /**
     * Faz o registro de um dispositivo móvel. Neste momento, o usuário ainda não estará habilitado para utilizar os
     * serviços. Um código de ativação será enviado para o usuário.
     * 
     * @param deviceId      Identificado do dispositivo móvel
     * @param deviceName    Nome do dispositivo
     * @param userName      Nome do usuário
     * @param email         Email
     * @param phoneNumber   Número do telefone
     * 
     * @return              String com o resultado da operação.          
     * 
     * @see activeDevice
     */
    public String deviceRegistration(String deviceId, String deviceName, String userName, String email, String phoneNumber) {

        if (deviceRegistred(deviceId)) {
            return "Error: already registered";
        }

        Device d = new Device();

        String currentStatus = "Pending";

        d.setDeviceId(deviceId);
        d.setDeviceName(deviceName);
        d.setUserName(userName);
        d.setEmail(email);
        d.setPhoneNumber(phoneNumber);
        d.setStatus(currentStatus);
        d.setActivationCode(generateCode());

        manager.persist(d);

        return currentStatus;
    }

    public Device getDevice(String deviceId) {

        Device d;

        try {
            d = (Device) manager.createNamedQuery("Device.findByDeviceId", Device.class).setParameter("deviceId", deviceId).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

        return d;

    }

    /**
     * Seleciona o prestador do serviço. 
     * 
     * @param messageType
     * @return 
     */
    public ServiceProvider getServiceProvider(Integer serviceProviderId) {

        ServiceProvider sp;
        
        // Integer xxx = new Integer(1);

        try {

            // Obs: No futuro deve ser aplicado um critério de seleção.
            //      Para o momento o critério é o primeiro que encontrar.
            sp = (ServiceProvider) manager.createNamedQuery("ServiceProvider.findByServiceProviderId", ServiceProvider.class).setParameter("serviceProviderId", serviceProviderId).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

        return sp;
    }
    
    
    public Occurrence getOccurrence(Integer id) {
        Occurrence o;
        
        try {
            
            o = (Occurrence) manager.createNamedQuery("Occurrence.findById", Occurrence.class).setParameter("id", id).getSingleResult();
            
        } catch (NoResultException e) {
            return null;
        } 
       
        return o;
    }
    
    
    private ServiceProvider getBestProvider(Integer messageType) {
        
        List<ServiceProvider> lsp;
        
        try {

            // Obs: No futuro deve ser aplicado um critério de seleção.
            //      Para o momento o critério é o primeiro que encontrar.
            lsp = (List<ServiceProvider>) manager.createNamedQuery("ServiceProvider.findByServiceId", ServiceProvider.class).setParameter("serviceId", messageType).getResultList();

            ServiceProvider sp;
            ServiceProvider bestSP;
            float qualifyRate = 0.0F; // 1.0 é a maior taxa de rejeição
            
            float   positive = 0.0F;
            float   negative = 0.0F;
            float   aux; 
            
            bestSP = lsp.get(0);
            
            for ( int i = 0; i < lsp.size(); i++) {
                                
                sp = lsp.get(i);
                
                if ( sp.getBusy() ) continue;

                positive = sp.getPositiveQualifies().floatValue();
                negative = sp.getNegativeQualifies().floatValue();
                
                aux = positive + negative;
                
                if ( aux > 0.0F ) {
                    
                    aux = 1.0F - (negative / aux );
                    if ( aux > qualifyRate  ) {
                        qualifyRate = aux;
                        bestSP = sp;
                    }
                }

            }

            return bestSP;
            
            
        } catch (NoResultException e) {
            return null;
        }       

    }

    public Boolean deviceRegistred(String deviceId) {

        Device d = getDevice(deviceId);

        if (d != null) {
            return true;
        }

        return false;
    }

    public String isRegistred(String deviceId) {

        Device d = getDevice(deviceId);

        if (d != null) {
            return "Registred";
        }

        return "Not Registred";
    }

    /**
     * Ativa um dispositivo previamente registrado com base no código de ativação informado.
     * 
     * 
     * @param deviceId      É o identificador do dispositivo
     * @param   typedCode   É o código informado pelo usuário
     * @return  String contendo o resultado da operação 
     * 
     * @see deviceRegistration
     */
    public String activeDevice(String deviceId, String typedCode) {

        Device d;

        try {
            d = (Device) manager.createNamedQuery("Device.findByDeviceId", Device.class).setParameter("deviceId", deviceId).getSingleResult();
        } catch (NoResultException e) {
            return "Error: Device not found";
        }

        // Verifica se o código digitado confere com o código do registro
        if (typedCode.equals(d.getActivationCode())) {
            String status = "Activated";
            d.setStatus(status);
            manager.persist(d);
            return status;
        }

        return "Error: typedCode does not match";
    }

    public String disactivateDevice(String deviceId) {

        Device d;

        try {
            d = (Device) manager.createNamedQuery("Device.findByDeviceId", Device.class).setParameter("deviceId", deviceId).getSingleResult();
        } catch (NoResultException e) {
            return "Error: Device not found";
        }
        String status = "Disactivated";
        d.setStatus(status);
        manager.persist(d);

        return "Disactivated";

    }

    /**
     * Obtem o status do dispositivo. 
     * 
     * @param   deviceId Identificação única do dispositivo;
     * @return  Srtring contendo a descição do status.
     */
    public String statusDevice(String deviceId) {
        Device d = getDevice(deviceId);

        if (d == null) {
            return "Error: Device not found";
        }

        return d.getStatus();

    }

    /**
     * Gera um código de três dígitos para confirmação de registro ou credenciamento.
     * 
     * @return  String contendo o código para confirmação. 
     */
    private String generateCode() {

        // Será implementado no futuro

        return "123";
    }
    
    
    /**
     * Envia solicitação (demanda) do usuáeio via Push.
     * 
     * @param fromDevice    Dispositivo móvel do solicitante (usuário)
     * @param toProvider    Prestador de serviço
     * @param message       Mensage da solicitação
     * @param latitude      Latitude  - Localização do demandante (usuário).
     * @param longitude     Longitude - Localização do usuário.
     */
    private void sendPush(Device fromDevice, ServiceProvider toProvider, String message, String latitude, String longitude) {
 
        
        
    }
    
    /**
     * Eenvia solicitação (demanda) do usuário via SMS.
     * 
     * @param fromDevice    Dispositivo móvel do solicitante (usuário)
     * @param toProvider    Prestador de serviço
     * @param message       Mensage da solicitação
     * @param latitude      Latitude  - Localização do demandante (usuário).
     * @param longitude     Longitude - Localização do usuário.
     */

    private void sendSMS(Device fromDevice, ServiceProvider toProvider, Occurrence occurrence) {

        
        
        // URL da Localização
         String longURL =   "http://ec2-50-19-193-4.compute-1.amazonaws.com:8080/Ops-war/DemandInformation?id="+ occurrence.getId();
        
        // Encurtando a URL para apresentar em SMS
        ShortenedUrl bitly = as("caratti","R_29aea572c67ec3d1f359daf14ba60595").call(shorten(longURL)); 
        String shortURL = bitly.getShortUrl();
        
        StringBuilder body = new StringBuilder("OPA!!!");
        body.append("\nHorário.....: ").append(occurrence.getDatetime().toString());
        body.append("\nSolicitante.: ").append(fromDevice.getUserName());
        body.append("\nFone........: ").append(fromDevice.getPhoneNumber());
        body.append("\nDetalhamenro: ").append(shortURL);
        
        try {
            SMSService sms = new SMSService();
            sms.enviarSMS(toProvider.getSmsDdd(), toProvider.getSmsNumber(), body.toString(), "opa", "a1k1ju77");
        } catch (Exception ex) {
            Logger.getLogger(OpaEJB.class.getName()).log(Level.SEVERE, null, ex);
            // System.out.println(ex.getMessage());
        }

    }

     /**
     * Eenvia solicitação (demanda) do usuário via EMail.
     * 
     * @param fromDevice    Dispositivo móvel do solicitante (usuário)
     * @param toProvider    Prestador de serviço
     * @param message       Mensage da solicitação
     * @param latitude      Latitude  - Localização do demandante (usuário).
     * @param longitude     Longitude - Localização do usuário.
     */   
    
    private void sendEmail(Device fromDevice, ServiceProvider toProvider, Occurrence occurrence) throws Exception {

        StringBuilder body = new StringBuilder(toProvider.getName() + ", há uma solicitação com as seguintes informações: ");


        InitialContext ic = new InitialContext();
        String snName = "opamail";
        Session session = (Session) ic.lookup(snName);

        Properties props = session.getProperties();
        props.put("mail.from", "opa@caratti.info");
        props.put("mail.smtp.user", "opa@caratti.info");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.password", "a1k1ju77");

        Message msg = new MimeMessage(session);
        msg.setSubject("OPA!!! - Solicitação de Serviço");
        msg.setSentDate(new Date());
        msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toProvider.getEmail(), false));

        body.append("\nHorário da solicitação..: ").append(occurrence.getDatetime().toString());
        body.append("\nNome do solicitante:\n").append(fromDevice.getUserName());
        body.append("\nTelefone de contato:\n").append(fromDevice.getPhoneNumber());
        body.append("\nMensagem:\n").append(occurrence.getMessage());
        // Converter para Link HTML
        body.append("\nClique no link para detalhamento da solicitação: ");
        body.append("http://ec2-50-19-193-4.compute-1.amazonaws.com:8080/Ops-war/DemandInformation?id=").append(occurrence.getId());

        msg.setText(body.toString());
        Transport.send(msg);

    }
    
    /**
     * Processa uma demanda do usuário por um determinado tipo de serviço.
     * 
     * @param deviceId      Dispositivo do usuário
     * @param latitude      Localização - Latitude
     * @param longitude     Localização - Longitude
     * @param messageType   Tipo da Mensagem (Classificação do serviço)
     * @param message       Mensagem
     * @return              Situação da solicitação.
     */
    public String sendOpa(String deviceId, String latitude, String longitude, Integer serviceId, String message) {


        // Obtem o prestador de serviço
        ServiceProvider sp = getBestProvider(serviceId);      
        if (sp == null ) {
            return "Prestador de serviço ainda não credenciado!";
        } 
        
        if (sp.getBusy() ) {
            return "Não há prestador de serviço dispónível no momento.";
        } 
        
        if ( sp.getCanBeBusy() )
            sp.setBusy(Boolean.TRUE);
        
        // Provisório
        int qtdPositive = sp.getPositiveQualifies().intValue();
        
        sp.setPositiveQualifies(qtdPositive  + 1);
        
   
        Device d = getDevice(deviceId);

        if (d == null) {
            return "Error: Device not found";
        }

        if (!(d.getStatus().equals("Activated"))) {
            return "Error: Device not activated";
        }

        
        
        Occurrence o = new Occurrence();

        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));

        Calendar cal = new GregorianCalendar(); // TimeZone.getTimeZone("GMT-3:00"));       

        o.setId(1);
        o.setDatetime(cal.getTime());
        o.setStatus("Sent");
        o.setServiceProviderId(sp.getServiceProviderId());
        o.setLatitude(latitude);
        o.setLongitude(longitude);
        o.setDeviceId(deviceId);
        o.setServiceId(serviceId);
        o.setMessage(message);

        manager.persist(o);
        
        // manager.persist(sp);
        
        manager.flush();
        manager.clear();

        
        // Envia para os meios selecionados pelo pretador de serviço
        if ( sp.getSendPush().booleanValue() ) {
            sendPush(d, sp, message, latitude, longitude);
        }
            

        if (sp.getSendSms().booleanValue()) {
            // Envia SMS
            sendSMS(d, sp, o);
        }
        

        if (sp.getSendEmail().booleanValue()) {

            try {
                sendEmail(d, sp, o);
            } catch (Exception ex) {
                Logger.getLogger(OpaEJB.class.getName()).log(Level.SEVERE, null, ex);
                // O que deve ser tratado aqui?????
            }
        }

        return "OK";
    }
    
    
    /**
     * Utilizado durante de desenvolvimento e testes.
     * @return 
     */
    public String resetServices() {


        List<ServiceProvider> lsp;

        try {

            lsp = (List<ServiceProvider>) manager.createNamedQuery("ServiceProvider.findAll", ServiceProvider.class).getResultList();

            ServiceProvider sp;

            for (int i = 0; i < lsp.size(); i++) {

                sp = lsp.get(i);

                sp.setBusy(Boolean.FALSE);
                sp.setPositiveQualifies(100);
                sp.setNegativeQualifies(10);

                manager.flush();

            }

            return "OK";


        } catch (NoResultException e) {
            return "Erro ao tentar configurar os serviços!";
        }

    }
    
    
    public String liberaPrestador(Integer serviceProviderId) {
        
        
        ServiceProvider sp = getServiceProvider(serviceProviderId);
        
        if ( sp == null ) {
            return "Prestador de Serviço não localizado";
        }
        
        sp.setBusy(Boolean.FALSE);
        
        manager.flush();
        manager.clear();
        
        return "OK";
    } 
    
}

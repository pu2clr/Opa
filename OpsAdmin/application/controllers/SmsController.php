<?php

class SmsController extends Zend_Controller_Action {
    

    public function init()
    {
        
    }
    
    
    public function messageAction()
    {
        $occurrence_id = $this->_getParam('id');
        
        $occurrence_model = new Application_Model_DbTable_Occurrence();
        $device_model = new Application_Model_DbTable_Device();
        $service_provider_model =  new Application_Model_DbTable_Serviceprovider();
        
        
        $occurrence_row = $occurrence_model->fetchRow("id = $occurrence_id");
        $device_id = $occurrence_row['device_id'];
        $device_row = $device_model->fetchRow("device_id = '$device_id'");
        
        $service_provider_id = $occurrence_row['service_provider_id'];
        $service_provider_row = $service_provider_model->fetchRow("service_provider_id = $service_provider_id");
        
        
        $this->_helper->layout()->setLayout('mapa');
        $this->view->occurrence = $occurrence_row;
        $this->view->device = $device_row;
        $this->view->service_provider = $service_provider_row;
        
    }
    
    public function liberaAction() {
        
        $service_provider_id = (int) $this->_getParam('id');
        
        $client = new Zend_Rest_Client('http://ec2-50-19-193-4.compute-1.amazonaws.com:8080/Ops-war/resources/br.eti.caratti.liberaprestador');
        $client->libera($service_provider_id);
        $result = $client->get();
        
        // $result = $client->libera($id)->get();
        
        var_dump($result);
        
        $this->view->message = $result;
        
        
    } 
    
}


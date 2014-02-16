<?php

class ServiceproviderController extends Zend_Controller_Action {

    protected $_model;
    
    public function init() {
        $this->_model = new Application_Model_DbTable_Serviceprovider();
    }
    
    public function listAction() {
        $aux = new Application_Model_DbTable_Vwservices();
        $this->view->serviceproviders = $aux->fetchAll();
        // $this->view->serviceproviders = $this->_model->fetchAll(null,'service_id asc');
    }

    public function createAction() {
        
        $form = new Application_Form_ServiceProvider();
        $form->setAction('serviceprovider/create');
        
        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost( $this->_request->getPost('service_provider_id') );
            
            if ($form->isValid($data)) {
                $this->_model->insert($data);
                $this->view->message = "Empresa cadastrada com sucesso.";
            }
            
          $this->_redirect('serviceprovider/list');   
        }

        $this->view->form = $form;
    }

    public function removeAction() {
        
        if ( $this->_hasParam('service_provider_id') == false )
        {
            $this->_redirect('serviceprovider/list');
        }
 
        $id = (int) $this->_getParam('service_provider_id');
        $where = $_model->getAdapter()->quoteInto('service_provider_id = ?', $id);
        $this->_model->delete($where);  
        $this->_redirect('serviceprovider/list'); 
        
    }

    public function updateAction() {
        
        $id = (int) $this->_getParam('service_provider_id');
        $result = $this->_model->find($id);
        $data = $result->current();

        if (null === $data) {
            $this->view->message = "Prestador de Serviço não encontrado!";
            return false;
        }

        $form = new Application_Form_ServiceProvider();
        $form->setAction(sprintf('serviceprovider/update/service_provider_id/%d', $id));
        $form->populate($data->toArray());

        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost($id);
            
            if ($form->isValid($data)) {
                $this->_model->update($data, "service_provider_id = $id");
                $this->_redirect('/serviceprovider/list');
            }
        }


        $this->view->form = $form;
    }

    public function indexAction() {
        $form = new Application_Form_ServiceProvider();
        $this->view->form = $form;
    }
    
    
    private function loadDataFromPost($id) 
    {
            $data = array(
                'service_provider_id' => $id,                 
                'service_id'          => $this->_request->getPost('service_id'),
                'name'                => $this->_request->getPost('name'),
                'email'               => $this->_request->getPost('email'),
                'sms_ddd'             => $this->_request->getPost('sms_ddd'),
                'sms_number'          => $this->_request->getPost('sms_number'),
                'send_sms'            => $this->_request->getPost('send_sms'),
                'send_email'          => $this->_request->getPost('send_email'),
                'busy'                => $this->_request->getPost('busy'),
                'can_be_busy'         => $this->_request->getPost('can_be_busy'),
                'positive_qualifies'  => $this->_request->getPost('positive_qualifies'),
                'negative_qualifies'  => $this->_request->getPost('negative_qualifies'),
                'status'              => $this->_request->getPost('status')
                );
            
            return $data;
    }

}


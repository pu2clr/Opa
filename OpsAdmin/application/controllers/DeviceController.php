<?php

class DeviceController extends Zend_Controller_Action {

   
    protected $_model;
    
    public function init() {
        $this->_model = new Application_Model_DbTable_Device();
    }
    
    public function listAction() {
        
        $this->view->devices = $this->_model->fetchAll();
    }

    public function createAction() {
        
        $form = new Application_Form_Device();
        $form->setAction('device/create');
        
        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost($this->_request->getPost('device_id'));


            if ($form->isValid($data)) {
                $this->_model->insert($data);
                $this->view->message = "Dispositivo cadastrado com sucesso.";
            }
          $this->_redirect('device/list');   
        }

        $this->view->form = $form;
    }

    public function removeAction() {
        
        if ( $this->_hasParam('device_id') == false )
        {
            $this->_redirect('device/list');
        }
 
        $id = $this->_getParam('device_id');
        $where = $this->_model->getAdapter()->quoteInto('device_id = ?', $id);
        $this->_model->delete($where);  
        $this->_redirect('device/list'); 
    }

    public function updateAction() {
        
        $id = $this->_getParam('device_id');
        
        $result = $this->_model->find($id);
        $data = $result->current();

        if (null === $data) {
            $this->view->message = "Dispositivo não encontrado!";
            return false;
        }
        
        $form = new Application_Form_Device();
        $form->setAction(sprintf('device/update/device_id/%d', $id));
        $form->populate($data->toArray());
        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost($id);
             
            if ($form->isValid($data)) {
                $this->_model->update($data, "device_id = $id");
                $this->_redirect('/device/list');
            }
        }
        

        $this->view->form = $form;
    }

    public function indexAction() {
        $form = new Application_Form_Device();
        $this->view->form = $form;
    }
    
    
    private function loadDataFromPost($id)
    {
        $data = array(
                'device_id'         =>  $id,
                'serial_number'     => $this->_request->getPost('serial_number'),
                'os_name'           => $this->_request->getPost('os_name'),
                'os_version'        => $this->_request->getPost('os_version'),            
                'device_name'       => $this->_request->getPost('device_name'),                
                'user_name'         => $this->_request->getPost('user_name'),
                'email'             => $this->_request->getPost('email'),
                'phone_ddd'         => $this->_request->getPost('phone_ddd'),
                'phone_number'      => $this->_request->getPost('phone_number'),
                'status'            => $this->_request->getPost('status'),
                'activation_code'   => $this->_request->getPost('activation_code')
        );
        
        return $data;
        
    }
    

}


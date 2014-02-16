<?php

class ServiceController extends Zend_Controller_Action {

   
    protected $_model;
    
    public function init() {
        $this->_model = new Application_Model_DbTable_Service();
    }
    
    public function listAction() {
        
        $this->view->services = $this->_model->fetchAll();
    }

    public function createAction() {
        
        $form = new Application_Form_Service();
        $form->setAction('service/create');
        
        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost($this->_request->getPost('service_id'));


            if ($form->isValid($data)) {
                $this->_model->insert($data);
                $this->view->message = "Serviço cadastrado com sucesso.";
            }
          $this->_redirect('service/list');   
        }

        $this->view->form = $form;
    }

    public function removeAction() {
        
        if ( $this->_hasParam('service_id') == false )
        {
            $this->_redirect('service/list');
        }
 
        $id = (int) $this->_getParam('service_id');
        $where = $this->_model->getAdapter()->quoteInto('service_id = ?', $id);
        $this->_model->delete($where);  
        $this->_redirect('service/list'); 
    }

    public function updateAction() {
        
        $id = (int) $this->_getParam('service_id');
        
        $result = $this->_model->find($id);
        $data = $result->current();

        if (null === $data) {
            $this->view->message = "Serviço não encontrado!";
            return false;
        }
        
        $form = new Application_Form_Service();
        $form->setAction(sprintf('service/update/service_id/%d', $id));
        $form->populate($data->toArray());
        if ($this->_request->isPost()) {
            
            $data = $this->loadDataFromPost($id);
             
            if ($form->isValid($data)) {
                $this->_model->update($data, "service_id = $id");
                $this->_redirect('/service/list');
            }
        }
        

        $this->view->form = $form;
    }

    public function indexAction() {
        $form = new Application_Form_Service();
        $this->view->form = $form;
    }
    
    
    private function loadDataFromPost($id)
    {
        $data = array(
                'service_id'          => $id,
                'service_type'        => $this->_request->getPost('service_type'),                
                'service_name'        => $this->_request->getPost('service_name'),
                'description'         => $this->_request->getPost('description'),
                'qtd_cases'           => $this->_request->getPost('qtd_cases'),
                'icon_name'           => $this->_request->getPost('icon_name'),
                'status'              => $this->_request->getPost('status')
        );
        
        return $data;
        
    }
    

}


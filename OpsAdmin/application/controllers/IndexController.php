<?php

class IndexController extends Zend_Controller_Action {

    public function init() {
        /* Initialize action controller here */
    }

    public function indexAction() {
        $this->view->assign('title', 'Alô mundo zend!');
    }
    
    function uploadAction()
    {
        $this->view->pageTitle = "Zend_Form File Upload Example";
        $this->view->bodyCopy = "<p>Please fill out this form.</p>";

        $form = new forms_Upload();

        if ($this->_request->isPost()) {
            $formData = $this->_request->getPost();
            if ($form->isValid($formData)) {
                
                // success - do something with the uploaded file
                $uploadedData = $form->getValues();
                Zend_Debug::dump($uploadedData, '$uploadedData');
                exit;
                
            } else {
                $form->populate($formData);
            }
        }

        $this->view->form = $form;
    }    


}


<?php

class UploadController extends Zend_Controller_Action {

  
    function uploadAction()
    {
        $this->view->pageTitle = "Zend_Form File Upload Example";
        $this->view->bodyCopy = "<p>Please fill out this form.</p>";

        $form = new Application_Form_Upload();

        if ($this->_request->isPost()) {
            $formData = $this->_request->getPost();
            if ($form->isValid($formData)) {
                
                // success - do something with the uploaded file
                $uploadedData = $form->getValues();
                Zend_Debug::dump($uploadedData, '$uploadedData');
                Zend_Debug::dump($form, 'Form');
                exit;
                
            } else {
                $form->populate($formData);
            }
        }

        $this->view->form = $form;
    }    


}
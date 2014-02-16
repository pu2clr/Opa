<?php

class Application_Form_Upload extends Zend_Form 
{ 
    public function __construct($options = null) 
    { 
        // $this->addElementPrefixPath('App', 'App/');
        parent::__construct($options);
        $this->setName('upload');
        $this->setAttrib('enctype', 'multipart/form-data');
        
        $description = new Zend_Form_Element_Text('description');
        $description->setLabel('Description')
                  ->setRequired(false)
                  ->addValidator('NotEmpty');

        $file = new Zend_Form_Element_File('file');
        $file->setLabel('File')
                 ->setRequired(true)
                 ->addValidator('NotEmpty')                         // Que não esteja vazio
                 ->addValidator('Count', false, 1)                  // Somente um arquivo
                 ->addValidator('Size', false, 1024000)             // Com tamanho máximo de 1M
                 ->addValidator('Extension', false, 'jpg,png,gif')  // Com estas extensões
                 ->setDestination(Zend_Registry::getInstance()->constants->UPLOAD_PATH);
             

        $submit = new Zend_Form_Element_Submit('submit');
        $submit->setLabel('Upload');
        
        $this->addElements(array($description, $file, $submit));
            

    } 
} 


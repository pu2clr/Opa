<?php
class Application_Form_Device extends Zend_Form
{

    public function init()
    {
        $this->setAction('index.php')
                ->setMethod('post');
        
        $device_id = new Zend_Form_Element_Text('device_id');
        $device_id->setLabel('Id. Dispositivo')->setAttrib('size', 60);
 
        $serial_number = new Zend_Form_Element_Text('serial_number');
        $serial_number->setLabel('No. Serial')->setAttrib('size', 60);        
        
        $os_name = new Zend_Form_Element_Text('os_name');
        $os_name->setLabel('Sistema')->setAttrib('size', 30);
        
        $os_version = new Zend_Form_Element_Text('os_version');
        $os_version->setLabel('Versão')->setAttrib('size', 30);        
        
        $device_name = new Zend_Form_Element_Text('device_name');
        $device_name->setLabel('Nome do Dispositivo')
                     ->setAttrib('size', 60)
                     ->addFilter('StripTags')
                     ->setRequired(true);        
        
        $user_name = new Zend_Form_Element_Text('user_name');
        $user_name->setLabel('Usuário')
                     ->setAttrib('size', 60)
                     ->addFilter('StripTags')
                     ->setRequired(true);

        $email = new Zend_Form_Element_Text('email');
        $email->setLabel('Email')
              ->setAttrib('size', 60)
              ->addFilter('StripTags')
              ->addValidator('EmailAddress');
        
        $phone_ddd = new Zend_Form_Element_Text('phone_ddd');
        $phone_ddd->setRequired(true)
                ->setLabel('DDD')
               ->setAttrib('size', 3);
        
        $phone_number = new Zend_Form_Element_Text('phone_number');
        $phone_number->setRequired(true)
                ->setLabel('Celular')
               ->setAttrib('size', 15);        
        
        $status = new Zend_Form_Element_Text('status');
        $status->setLabel('Situação')
              ->setAttrib('size', 15)
              ->addFilter('StripTags');

        
        $activation_code = new Zend_Form_Element_Text('activation_code');
        $activation_code->setLabel('Cód. Ativação')
                  ->setAttrib('size', 4);
        
        $submit = new Zend_Form_Element_Submit('enviar');
        $submit->setLabel('Salvar');
        
        
        $this->addElements(array($device_id, $serial_number, $os_name, $os_version, $device_name, $user_name, $email,$phone_ddd, $phone_number, $status, $activation_code, $submit));
                
    }


}

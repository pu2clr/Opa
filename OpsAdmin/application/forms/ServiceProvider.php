<?php

class Application_Form_ServiceProvider extends Zend_Form
{

    public function init()
    {
        $this->setAction('index.php')
                ->setMethod('post');
        
        $service = new Zend_Form_Element_Select('service_id');
        $service->setLabel('Serviço');
        
        $table = new Application_Model_DbTable_Service();
        foreach ($table->fetchAll() as $row) {
            $service->addMultiOption($row->service_id, $row->service_name);
        }
        
        
        $name = new Zend_Form_Element_Text('name');
        $name->setLabel('Nome')
             ->setAttrib('size', 60)
             ->addFilter('StripTags');

        
        $email = new Zend_Form_Element_Text('email');
        $email->setLabel('Email')
              ->setAttrib('size', 60)
              ->addFilter('StripTags')
              ->addValidator('EmailAddress');
        
        $sms_ddd = new Zend_Form_Element_Text('sms_ddd');
        $sms_ddd->setRequired(true)
                ->setLabel('DDD')
               ->setAttrib('size', 3);
        
        $sms_number = new Zend_Form_Element_Text('sms_number');
        $sms_number->setRequired(true)
                ->setLabel('SMS')
               ->setAttrib('size', 15);        
        
        $send_sms = new Zend_Form_Element_Checkbox('send_sms');
        $send_sms->setLabel('Envia SMS');
        
        $send_email = new Zend_Form_Element_Checkbox('send_email');        
        $send_email->setLabel('Envia EMail');
        
        $busy = new Zend_Form_Element_Checkbox('busy');        
        $busy->setLabel('Ocupado?');
        
        $can_be_busy = new Zend_Form_Element_Checkbox('can_be_busy');        
        $can_be_busy->setLabel('Fica ocupado?');
        
        
        $positive_qualifies = new Zend_Form_Element_Text('positive_qualifies');
        $positive_qualifies->setRequired(true)
                ->setLabel('Qualif. Positivas')
               ->setAttrib('size', 6);           
        
        $negative_qualifies = new Zend_Form_Element_Text('negative_qualifies');
        $negative_qualifies->setRequired(true)
                ->setLabel('Qualif. Positivas')
               ->setAttrib('size', 6);


        $status = new Zend_Form_Element_Checkbox('status');
        $status->setLabel('Ativo?');

        $submit = new Zend_Form_Element_Submit('enviar');
        $submit->setLabel('Salvar');
        
        
        $this->addElements(array($service, $name, $email, $sms_ddd, $sms_number,
                                 $send_sms, $send_email, $busy, $can_be_busy, 
                                 $positive_qualifies,$negative_qualifies, $status, $submit));
        
    }


}


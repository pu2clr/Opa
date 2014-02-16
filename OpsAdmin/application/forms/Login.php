<?php

class Application_Form_Login extends Zend_Form
{

    public function init()
    {
        $this->addElement('text', 'email')
                ->set;
        $this->addElement('password', 'password');
        $this->addElement('submit', 'Autenticar');
        
        
        
    }
}
    
?>
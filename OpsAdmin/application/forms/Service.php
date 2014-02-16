<?php
class Application_Form_Service extends Zend_Form
{

    public function init()
    {
        $this->setAction('index.php')
                ->setMethod('post');
        
        $service_id = new Zend_Form_Element_Text('service_id');
        $service_id->setLabel('Id. Serviço');
        
        $service_type = new Zend_Form_Element_Select('service_type');
        $service_type->setLabel('Classificação')
                ->addMultiOption('PUBLICO','PUBLICO')
                ->addMultiOption('PARTICULAR','PARTICULAR');
        
        $service_name = new Zend_Form_Element_Text('service_name');
        $service_name->setLabel('Nome')
                     ->setAttrib('size', 60)
                     ->addFilter('StripTags')
                     ->setRequired(true);

        
        $description = new Zend_Form_Element_Text('description');
        $description->setLabel('Descrição')
              ->setAttrib('size', 120)
              ->addFilter('StripTags');

        
        $qtd_cases = new Zend_Form_Element_Text('qtd_cases');
        $qtd_cases->setLabel('Ocorrências')
                  ->setAttrib('size', 6);

        $icon_name = new Zend_Form_Element_Text('icon_name');
        $icon_name->setLabel('Nome da Imagem')
            ->setAttrib('size', 60);

        $status = new Zend_Form_Element_Checkbox('status');
        $status->setLabel('Ativo?');

        $submit = new Zend_Form_Element_Submit('enviar');
        $submit->setLabel('Salvar');
        
        
        $this->addElements(array($service_id, $service_type, $service_name, $description,$qtd_cases, $icon_name, $status, $submit));
                
    }


}


<?php

class Application_Model_DbTable_Serviceprovider extends Zend_Db_Table_Abstract {

    protected $_name = 'service_provider';
    
    
    public function insert(array $data) {
        
        parent::insert($data);
    }

}

?>

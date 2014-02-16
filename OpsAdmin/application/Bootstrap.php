<?php

class Bootstrap extends Zend_Application_Bootstrap_Bootstrap {

    protected function _initConstants() {
        $registry = Zend_Registry::getInstance();
        $registry->constants = new Zend_Config($this->getApplication()->getOption('constants'));
    }
}


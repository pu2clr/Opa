<?php

class OparestController extends Zend_Controller_Action { // Zend_Rest_Controller {

    protected $opa;

    public function init() {
        $this->_helper->viewRenderer->setNoRender(true);
        $this->_helper->layout()->disableLayout();
        $this->getResponse()->setBody(null);

        // Instancia a classe de negócio
        $this->opa = new Application_Model_Opa();
    }

    /**
     * Devolve ao solicitante um string no formato JSON com os serviços utilizados
     * com maior frequência.
     * 
     * Link de teste: http://localhost:8888/OpsAdmin/public/oparest/service/servicename/topservices
     *                http://ec2-174-129-183-10.compute-1.amazonaws.com//OpsAdmin/public/oparest/service/servicename/topservices
     * 
     */
    public function topservicesAction() {

        $result = Array('topservices' => $this->opa->getTopServices());
        echo Zend_Json::encode($result);
    }

    /**
     * Devolve ao solicitante um string no formato JSON com os demais serviços.
     * Exceto os serviços contidos no conjunto anterior (topservices).
     * 
     * Link de teste: http://localhost:8888/OpsAdmin/public/oparest/service/servicename/allservices
     *                http://ec2-174-129-183-10.compute-1.amazonaws.com//OpsAdmin/public/oparest/service/servicename/allservices
     *   
     */
    public function allservicesAction() {

        $result = Array('allservices' => $this->opa->getAllServices());
        echo Zend_Json::encode($result);
    }

    /**
     * Registra uma solicitação de serviço.
     * Os parâmetros são enviados via requisição e estão descritos a seguir:
     * 
     * @param deviceId      - Identificação do dispositivo do solicitante
     * @param latitude      - Localização (Latitude)
     * @param longitude     - Localização (Longitude) 
     * @param serviceId     - Código da identificação do Serviço
     * @param message       - Mensagem
     * 
     * @return String JSON com o resultado da operação 
     */
    public function requestserviceAction() {
        $deviceId = $this->_getParam('deviceId');
        $latitude = $this->_getParam('latitude');
        $longitude = $this->_getParam('longitude');
        $serviceId = $this->_getParam('serviceId');
        $message = $this->_getParam('message');
        $mediaType = $this->_getParam('mediaType');
        $fileName = $this->_getParam('fileName');
        $result = Array('result' => $this->opa->requestService($deviceId, $latitude, $longitude, $serviceId, $message, $mediaType, $fileName));
        echo Zend_Json::encode($result);
    }

    /**
     * Libera um prostador de serviço para atender a uma nova apresentação.
     * O parâmetro é enviado via requisição e está descrito a seguir:
     * 
     * @param serviceProviderId - Código de identificação do prestador de serviço.
     * 
     * @return String JSON com o resultado da operação.
     */
    public function unlockproviderAction() {
        $serviceProviderId = $this->_getParam('serviceProviderId');
        $result = Array('result' => $this->opa->unlockProvider($serviceProviderId));
        echo Zend_Json::encode($result);
    }

    /**
     * Esta ação existe somente para manter a compatibilidade com a versão anterior
     * 
     * Informa se um determinado dispositivo está registrado no sistema.
     * 
     * @param deviceId      - Código de identificação única do dispositivo.
     *   
     * @return String JSON Indicado Registrado ou não registrado 
     */
    public function isregistredAction() {
        $deviceId = $this->_getParam('deviceId');
        $aux = ($this->opa->getStatusDevice($deviceId) == "Device not found") ? 'Not Registred' : 'Registred';
        $result = Array('result' => $aux);
        echo Zend_Json::encode($result);
    }

    /**
     * Registra um dispositivo no sistema.
     * 
     * @param deviceId      - Código de identificação único no sistema
     * @param deviceName    - Nome do dispositivo
     * @param userName      - Nome do usuário do dispositivo
     * @param email         - Endereço de email do usuário do dispositivo
     * @param phoneNumber   - Número do telefone do usuário do dispositivo
     * 
     * @return String JSON com o resultado da operação                 
     */
    public function registerdeviceAction() {
        $deviceId = $this->_getParam('deviceId');
        $serialNumber = $this->_getParam('serialNumber');
        $osName = $this->_getParam('osName');
        $osVersion = $this->_getParam('osVersion');
        $deviceName = $this->_getParam('deviceName');
        $userName = $this->_getParam('userName');
        $email = $this->_getParam('email');
        $phoneDDD = $this->_getParam('phoneDDD');
        $phoneNumber = $this->_getParam('phoneNumber');
        $result = Array('result' => $this->opa->registerDevice($deviceId, $serialNumber, $osName, $osVersion, $deviceName, $userName, $email, $phoneDDD, $phoneNumber));
        echo Zend_Json::encode($result);
    }

    /**
     * Ativa um dispositivo previamente registrado com base no código de ativação informado.
     * 
     * @param  deviceId      É o identificador do dispositivo
     * @param  typedCode   É o código informado pelo usuário
     * @return String contendo o resultado da operação 
     */
    public function activedeviceAction() {
        $deviceId = $this->_getParam('deviceId');
        $typedCode = $this->_getParam('typedCode');
        $result = Array('result' => $this->opa->activeDevice($deviceId, $typedCode));
        echo Zend_Json::encode($result);
    }

    /**
     * Informa o status do dispositivo. Isto é: Ativo, Inativo ou pendente.
     * Útil no processo de homologação do dispositivo e também para desabilitá-lo
     * em algumas situações.
     * 
     * @param deviceId      - Código de identificação único do dispositivo.
     * 
     * @return String JSON indicando o status atual do dispositivo.
     */
    public function statusdeviceAction() {
        $deviceId = $this->_getParam('deviceId');
        // echo Zend_Json::encode($this->opa->getStatusDevice($deviceId));
        $result = Array('result' => $this->opa->getStatusDevice($deviceId));
        echo Zend_Json::encode($result);
    }

    /**
     * Uso interno. Inicia o sistema para execução de testes.
     *
     */
    public function resetAction() {
        $result = Array('result' => $this->opa->resetOpa());
        echo Zend_Json::encode($result);
    }
    
    

    public function pushAction() {

        $message = "Text to send";
        $badgeCount = 1;
        $sound = "default";
        $payload['aps'] = array('alert' => $message,
            'badge' => $badgeCount,
            'sound' => $sound);
        $payload = json_encode($payload);
        $deviceToken = 'c902XXX556dc5581f2750XXX97ea8c496XXXa613fafXXX50cb356749XXX07cf1';
        $apnsHost =
                'gateway.sandbox.push.apple.com';
        $apnsPort = 2195;
        $apnsCert = 'apns-dev.pem';
        // Estabelece uma conexão com o servidor
        $streamContext = stream_context_create();
        stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
        $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 2, STREAM_CLIENT_CONNECT, $streamContext);
        $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', str_replace(' ', '', $deviceToken)) . chr(0) . chr(strlen($payload)) . $payload;
        fwrite($apns, $apnsMessage);
        fclose($apns);
    }

}

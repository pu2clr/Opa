<?php

/**
 * Esta classe implementa os principais serviÃ§os do sistema Opa.
 * 
 */

/*
define ("OPA_EMAIL","opa@caratti.eti.br");
define ("OPA_USER","ora@caratti.eti.br");
define ("OPA_PASSWD","a1k1ju77");
define ("OPA_URL_BASE", "http://www.consultoriaavaliar.com.br");


define ("BITLY_LOGIN","caratti");
define ("BITLY_APPKEY","R_29aea572c67ec3d1f359daf14ba60595");
define ("BITLY_URL","http://api.bit.ly/v3/shorten");

define ("BYJG_LOGIN","opa");
define ("BYJG_PASSWD", "a1k1ju77");

define ("SMS_ERROR", "Erro no serviÃ§o de SMS");
*/


class Application_Model_Opa {

    
    /**
     * Gera um cÃ³digo de trÃªs dÃ­gitos para confirmaÃ§Ã£o de registro ou credenciamento.
     * 
     * @return type String contendo o cÃ³digo para confirmaÃ§Ã£o.
     */
    private function generateCode() {
       
        $code = rand(100,999);
        return $code;
    }
    
    
    /**
     * Utiliza o serviÃ§o da  Bit.ly para encurtar uma URL.
     * @param type $url String 
     * @return type String URL curta
    */ 
    private function getShortBitlyURL($url) {
        $login = 'caratti';
        $appkey = 'R_29aea572c67ec3d1f359daf14ba60595';
        $format = 'txt';
        $connectURL = 'http://api.bit.ly/v3/shorten?login=' . $login . '&apiKey=' . $appkey . '&uri=' . urlencode($url) . '&format=' . $format;
 
        $ch = curl_init();
        $timeout = 5;
        curl_setopt($ch, CURLOPT_URL, $connectURL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, $timeout);
        $data = curl_exec($ch);
        curl_close($ch);
        return $data;
    }
    

    /**
     * Obtem os seis serviÃ§os mais utilizados
     * 
     * @return type  String com a lista de serviÃ§os mais utilizados
     */
    public function getTopServices() {
        $device_model = new Application_Model_DbTable_Topservices();
        $result = $device_model->fetchAll();
        return $result->toArray();
    }

    /**
     * Obtem os demais serviÃ§os. Ou seja, obtem todos os serviÃ§os exceto os que estÃ£o
     * contidos no conjunto dos mais utilizados (Ver acima)
     * 
     * @return type String com a lista dos demais serviÃ§os.
     */
    public function getAllServices() {
        $device_model = new Application_Model_DbTable_Allservices();
        $result = $device_model->fetchAll();
        // Verificar
        return $result->toArray();
    }

    /**
     * Obtem as informaÃ§Ãµes do um dado dispositivo 
     * @param type $deviceId  String    - IdentificaÃ§Ã£o do Dispositivo
     * @return type String          - InformaÃ§Ãµes sobre o dispositivo
     */
    public function getDevice($deviceId) {
        $device_model = new Application_Model_DbTable_Device();
        $result = $device_model->find($deviceId);
        return $result->current();
    }

    /**
     * Obtem o status de um dispositivo. 
     * Pode ser usado tambÃ©m para verificar se um dispositivo estÃ¡ registrado.
     * 
     * @param type  String  $deviceId    - IdentificaÃ§Ã£o Ãºnica do dispositivo
     * @return type String contendo o Status do dispositivo. Caso o dispositivo
     *                     nÃ£o esteja cadastrado retornarÃ¡ "Device not found".  
     */
    public function getStatusDevice($deviceId) {
        $device_model = new Application_Model_DbTable_Device();
        $result = $device_model->find($deviceId);

        $row = $result->current();

        if ($row != null)
            return $row->status;

        return 'Device not found';
    }
    

    /**
     * Obtem as informaÃ§Ãµes sobre um dado serviÃ§o
     * @param type $derviceId Integer - CÃ³digo de IdentificaÃ§Ã£o do ServiÃ§o
     * @return type String       - InformaÃ§Ãµes sobre os serviÃ§o
     */
    public function getService($serviceId) {
        $device_model = new Application_Model_DbTable_Service();
        $result = $device_model->find($serviceId);
        return $result->current();
    }
    

    /**
     * Obtem informaÃ§Ãµes sobre o provedor de um dado serviÃ§o
     * @param type $serviceProviderId Integer - CÃ³digo de IdentificaÃ§Ã£o do prestador de serviÃ§o
     * @return type String               - InformaÃ§Ãµes sobre o prestador de serviÃ§o
     */
    public function getServiceProvider($serviceProviderId) {
        $sp_model = new Application_Model_DbTable_Serviceprovider();
        $result = $sp_model->find($serviceProviderId);
        return $result->current();
    }

    
    /**
     * Obtem o prestador de serviÃ§o com melhor condiÃ§Ãµes para atender a solicitaÃ§Ã£o.
     * Isso inclui: 
     *      EstÃ¡ disponÃ­vel (LIVRE)
     *      Ter a menor taxa de rejeiÃ§Ã£o
     *      
     * @param type $serviceId       - CÃ³digo de IdentificaÃ§Ã£o do serviÃ§o
     * @return type                 - Row - O prestador de ServiÃ§o
     */
    public function getBestProvider($serviceId) {

        $sp_model = new Application_Model_DbTable_Serviceprovider();

        // Relaciona os prestadores de serviÃ§o de um dado serviÃ§o que esteja LIVRE
        $resultList = $sp_model->fetchAll("service_id = $serviceId and busy = 0");

        
        if (!$resultList->current())
            return null;

        $betsSP = null;
        $qualifyRate = 0.0;


        foreach ($resultList as $row) {
            $sumQualifies = $row->positive_qualifies + $row->negative_qualifies;
            if ($sumQualifies > 0) {
                $fee = 1.0 - $row->negative_qualifies / $sumQualifies;
                if ($fee > $qualifyRate) {
                    $qualifyRate = $fee;
                    $bestSP = $row;
                }
            }
        }

        return $bestSP;
    }

    private function sendPush($device, $serviceProvider, $occurrence) {
        
    }

    
    /**
     * Envia um SMS via serviÃ§o da ByJG
     * Envia um SMS para o prestador de serviÃ§o credenciado.
     *  
     * @param type $device
     * @param type $serviceProvider
     * @param type $occurrence
     * @return type 
     */
    private function sendSMSSOAP($device, $serviceProvider, $occurrence) {
   
        $longURL = 'http://www.consultoriaavaliar.com.br/OpsAdmin/public/sms/message/id/' . $occurrence['id'];
        $shortURL = $this->getShortBitlyURL($longURL);
        
        $userName =  $device->user_name;
        $dateTime = $occurrence['datetime'];
        

        $message = "O Sr(a) $userName,  fez uma solicitaÃ§Ã£o as $dateTime. Mais detalhe: $shortURL"; 
        
 	// Definindo o webservice
	$client = new SoapClient(NULL,
	array(
		"location" => Zend_Registry::getInstance()->constants->BYJG_URL,
		"uri"      => "urn:xmethods-delayed-quotes",
		"style"    => SOAP_RPC,
		"use"      => SOAP_ENCODED
		)
	);

	// Chamando mÃ©todo do webservice
	$result = $client->__call(
		"enviarSMS",
	        array(
		    new SoapParam($serviceProvider->sms_ddd, "ddd"),
		    new SoapParam($serviceProvider->sms_number, "celular"),
		    new SoapParam($message, "mensagem"),
		    new SoapParam(Zend_Registry::getInstance()->constants->BYJG_LOGIN, "usuario"),
		    new SoapParam(Zend_Registry::getInstance()->constants->BYJG_PASSWD, "senha"),
	        ),
	        // OpÃ§Ãµes
	        array(
	            "uri" => "urn:xmethods-delayed-quotes",
	            "soapaction" => "urn:xmethods-delayed-quotes#getQuote"
	        )
	);
        

	return $result;       
        
    }

    
    /**
     * Envia uma mensagem via email para o prestador de serviÃ§o.
     * @param type $device              - Dispositivo do solicitante (informaÃ§Ãµes do solicitante)
     * @param type $serviceProvider     - Prestador de serviÃ§o
     * @param type $occurrence          - OcorrÃªncia (InformaÃ§Ãµes sobre a demanda)
     */
    private function sendEmailToProvider($device, $serviceProvider, $occurrence) {

        $link = 'http://www.consultoriaavaliar.com.br/OpsAdmin/public/sms/message/id/' . $occurrence['id'];
        $subject = 'OPA!!! - SolicitaÃ§Ã£o de ServiÃ§o';

        $budy = '<br>HorÃ¡rio da solicitaÃ§Ã£o..........: ' . $occurrence['datetime'];
        $budy .= '<br>Nome do solicitante.............: ' . $serviceProvider->name;
        $budy .= '<br>Telefone de contato.............: ' . $serviceProvider->phone_number;
        $budy .= '<br>Mensagem: <br><br>' . $occurrence['message'] . '<br>';
        $budy .= '<br><a href="' . $link . '">Clique aqui para detalhar</a>';

        $config = array('auth' => 'login',
                        'username' => Zend_Registry::getInstance()->constants->OPA_USER,
                        'password' => Zend_Registry::getInstance()->constants->OPA_PASSWD
                        );

        $transport = new Zend_Mail_Transport_Smtp(Zend_Registry::getInstance()->constants->OPA_SMTP, $config);
        Zend_Mail::setDefaultTransport($transport);


        $mail = new Zend_Mail('utf-8');

        $mail->setFrom(Zend_Registry::getInstance()->constants->OPA_EMAIL);        
        $mail->addTo($serviceProvider->email);
        $mail->setBodyHtml($budy,'utf-8','utf-8');
        $mail->setSubject($subject);
        $mail->send();
    }

    /**
     * Registra uma solicitaÃ§Ã£o (ocorrÃªncia ou demanda).
     * 
     * @param type $deviceId    - Dispositivo do solicitante (usuÃ¡rio)
     * @param type $latitude    - Latitude (localizaÃ§Ã£o geogrÃ¡fica do dispositivo mÃ³vel)
     * @param type $longitude   - Longitude
     * @param type $serviceId   - IdentificaÃ§Ã£o do serviÃ§o solicitado
     * @param type $message     - Mensagem
     * @return type String com o resultado da operaÃ§Ã£o.
     */
    public function requestService($deviceId, $latitude, $longitude, $serviceId, $message, $mediaType, $fileName) {


        $service = $this->getService($serviceId);
        
        if (!$service)
            return 'Este serviÃ§o nÃ£o foi localizado!';



        $bestSP = $this->getBestProvider($serviceId);

        if (!$bestSP)
            return 'NÃ£o hÃ¡ prestador de serviÃ§o disponÃ­vel para este serviÃ§o no momento. Tente mais darde.';

        // Verifica se o prestador de serviÃ§o pode ficar ocupado.
        // Caso afirmativo, atribui o status de ocupado para ele.
        if ($bestSP->can_be_busy) {
            $bestSP->busy = 1;
            $bestSP->save();
        }


        $device = $this->getDevice($deviceId);

        if (!$device)
            return 'Este dispositivo nÃ£o localizado!';

        if ($device->status != 'Activated')
            return 'Este dispositivo nÃ£o estÃ¡ habilitado';

        // Continua.

        date_default_timezone_set('America/Sao_Paulo');
        $datetime = Zend_Date::now()->toString('yyyyMMddHHmmss');

        $data = Array(
            'device_id' => $deviceId,
            'datetime' => $datetime,
            'status' => 'Sent',
            'service_provider_id' => $bestSP->service_provider_id,
            'latitude' => $latitude,
            'longitude' => $longitude,
            'phone_number' => null,
            'service_id' => $serviceId,
            'message' => $message,
            'media_type' => $mediaType,
            'file_name' => $fileName
        );

        $occurrence_model = new Application_Model_DbTable_Occurrence();
        $data['id'] = $occurrence_model->insert($data);
        $data['datetime'] = Zend_Date::now()->toString('dd/MM/yyyy-HH:mm:ss');
        
        $result = 'OK';

        if ($bestSP->send_push) {
            $this->sendPush($device, $bestSP, $data);
        }

        if ($bestSP->send_sms) {
           $this->sendSMSSOAP($device, $bestSP, $data);
        }

        if ($bestSP->send_email) {
          $this->sendEmailToProvider($device, $bestSP, $data);
        }


        return 'OK';
    }
    
    /**
     * Envia uma mensagem via SMS para um dado nÃºmero 
     * 
     * @param type $ddd             - CÃ³digo DDD
     * @param type $phoneNumber     - NÃºmero do telefone
     * @param type $message         - Mansagem a ser enviada
     * @return type  String com o resultado da operaÃ§Ã£o.               
     */
    private function sendSMS($ddd, $phoneNumber, $message) {

 	// Definindo o webservice
	$client = new SoapClient(NULL,
	array(
		"location" => Zend_Registry::getInstance()->constants->BYJG_URL,
		"uri"      => "urn:xmethods-delayed-quotes",
		"style"    => SOAP_RPC,
		"use"      => SOAP_ENCODED
		)
	);

	// Chamando mÃ©todo do webservice
	$result = $client->__call(
		"enviarSMS",
	        array(
		    new SoapParam($ddd, "ddd"),
		    new SoapParam($phoneNumber, "celular"),
		    new SoapParam($message, "mensagem"),
		    new SoapParam(Zend_Registry::getInstance()->constants->BYJG_LOGIN, "usuario"),
		    new SoapParam(Zend_Registry::getInstance()->constants->BYJG_PASSWD, "senha"),
	        ),
	        // OpÃ§Ãµes
	        array(
	            "uri" => "urn:xmethods-delayed-quotes",
	            "soapaction" => "urn:xmethods-delayed-quotes#getQuote"
	        )
	);

	return $result;       
        
    }


    /**
     * Envia uma mensagem via sistema WhatApp 
     * 
     * @param type $ddd             - CÃ³digo DDD
     * @param type $phoneNumber     - NÃºmero do telefone
     * @param type $message         - Mansagem a ser enviada
     * @return type  String com o resultado da operaÃ§Ã£o.               
     */
    private function sendWhatApp($ddd, $phoneNumber, $message) {
 	$key = '4185a5bd4deccb08797b9f17c7ac0f0a';
	$cc = '55';   
      	$url="http://api.whatsapp-api.com/connector/auth?".
         http_build_query(
         array(
         "key"=>$key,
         "cc"=>$cc,
         "number"=>$ddd.$phoneNumber,
         "msg"=>$message,
         ));
      $rs=json_decode(file_get_contents($url), true);
      $result=$rs["result"];
     
      if (isset( $rs["reason"]) ) 
	  $reason=$rs["reason"];
      else 
	  unset($reason);
      
      if($result)
    	 return 'OK';
      else 
         return $reason;		
    }
 
    /**
     * Envia uma mensagem de email.
     * 
     * @param type $email       - endereÃ§o de email
     * @param type $message     - Mensagem
     * @param type $subject     - Assunto
     */
    private function sendEmail ( $email, $message, $subject) {
 
        $config = array('auth' => 'login',
                        'username' => Zend_Registry::getInstance()->constants->OPA_USER,
                        'password' => Zend_Registry::getInstance()->constants->OPA_PASSWD
                        );

        // $transport = new Zend_Mail_Transport_Smtp('smtp.caratti.info', $config);
        $transport = new Zend_Mail_Transport_Smtp(Zend_Registry::getInstance()->constants->OPA_SMTP, $config);
        Zend_Mail::setDefaultTransport($transport);

        $mail = new Zend_Mail('utf-8');

        $mail->setFrom(Zend_Registry::getInstance()->constants->OPA_EMAIL);
        $mail->addTo($email);
        $mail->setBodyHtml($message,'utf-8','utf-8');
        $mail->setSubject($subject);
        $mail->send();
    }
        

    /**
     * Para que um usuÃ¡rio possa utilizar o sistema, Ã© necesÃ¡rio que seu 
     * dispositivo mÃ³vel seja registrado e homologado. 
     * Este mÃ©todo faz o registro de um dispositivo mÃ³vel. Esta Ã© a primeira 
     * etapa do processo de homologaÃ§Ã£o de um dispositivo.  
     * 
     * @param type $deviceId        - IdentificaÃ§Ã£o Ãºnica do dispositivo
     * @param type $deviceName      - Nome do dispositivo
     * @param type $userName        - Nome do UsuÃ¡rio
     * @param type $email           - Email do usuÃ¡rio
     * @param type $phoneNumber     - Telefone do UsuÃ¡rio
     * 
     * @return type String com o resultado da operaÃ§Ã£o
     */
    public function registerDevice($deviceId, $serialNumber, $osName, $osVersion, $deviceName, $userName, $email, $phoneDDD, $phoneNumber) {

        $device = $this->getDevice($deviceId);

        if ( !$device == null )
            return 'Device already registered';
        
        $device_model = new Application_Model_DbTable_Device();
 
        date_default_timezone_set('America/Sao_Paulo');
        $datetime = Zend_Date::now()->toString('yyyyMMddHHmmss');
       
        
        $activation_code = $this->generateCode();
        
        $data = Array(
            'device_id'         => $deviceId,
            'serial_number'     => $serialNumber,
            'os_name'           => $osName,
            'os_version'        => $osVersion,
            'device_name'       => $deviceName,
            'user_name'         => $userName,
            'email'             => $email,
            'phone_ddd'         => $phoneDDD,
            'phone_number'      => $phoneNumber,
            'status'            => 'Pending',
            'activation_code'   => $activation_code,
            'datetime'          => $datetime
        );
        
        
        $result = $device_model->insert($data);
        
        if ($result != $deviceId)
            return 'Device could not be registred';
        
   
        $message  = "Seu cÃ³digo de ativaÃ§Ã£o Ã©: $activation_code. ";
        $message .= 'O Opa!!! irÃ¡ solicitar este nÃºmero para habilitar o dispositivo.';
        
        $this->sendSMS($phoneDDD, $phoneNumber, $message);
        
        return 'Pending';
        
    }
    
    
    /**
     * Habilita um dispositivo mÃ³vel para utilizar o sistema.
     * Para tanto, verifica se o cÃ³digo de ativaÃ§Ã£o confere com o informado pelo
     * usuÃ¡rio.
     * 
     * @param type $deviceId    - IdentificaÃ§Ã£o Ãºnica do dispositivo.
     * @param type $typedCode   - CÃ³digo de ativaÃ§Ã£o digitado pelo usuÃ¡rio.
     * 
     * @return type String com o resultado da operaÃ§Ã£o.
     */
    public function activeDevice($deviceId, $typedCode) {
        
        $device = $this->getDevice($deviceId);
        
        if ( $device == null )
            return 'Device not found';
        
        if ( $device->activation_code != $typedCode ) {
           return 'Typed code does not match'; 
        }
       
        date_default_timezone_set('America/Sao_Paulo');
        $datetime = Zend_Date::now()->toString('yyyyMMddHHmmss');
         
        $device->status = 'Activated';
        $device->activation_date = $datetime;
        $device->save();
        
        $message = 'ParabÃ©ns, vocÃª agora Ã© um usuÃ¡rio do Opa!!!. Seu dispositivo estÃ¡ habilitado.';
        $subject = 'Opa!!! Dispositivo Habilitado.';
        $this->sendEmail($device->email, $message, $subject);
        
        return 'Activated';
    }
    
    
    public function unlockProvider($serviceProviderId) {
        
        $sp = $this->getServiceProvider($serviceProviderId);
        
        $sp->busy = 0;
        $sp->save();
        
        return 'OK';
        
    }
    
    
    /**
     * Uso interno. SerÃ¡ removida
     * @return type 
     */
    public function resetOpa() {
        
        $sp_model = new Application_Model_DbTable_Serviceprovider();
        $resultList = $sp_model->fetchAll();
        
        foreach ($resultList as $row) {
            $row->positive_qualifies = 100;
            $row->negative_qualifies = 10;
            $row->busy = 0;
            $row->save();
        }

        return 'OK';        
        
    }

}
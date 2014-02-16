/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package byjg.webservices.sms;

import byjg.webservices.ByJGBaseWebService;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe para abstrair as chamadas ao WebService ByJG para SMS
 * @author jg
 */
public class SMSService extends ByJGBaseWebService
{
    protected static final String SERVICE = "sms";

    /**
     * Retorna a versao do WebService
     * @return Versão
     * @throws Exception
     */
    public String obterVersao() throws Exception
    {
        return this.executeWebService(SMSService.SERVICE, "obterVersao", null);
    }

    /**
     * Enviar um SMS
     * @param ddd
     * @param celular
     * @param mensagem
     * @param usuario
     * @param senha
     * @return Situação. Ver manual.
     * @throws Exception
     */
    public String enviarSMS(String ddd , String celular , String mensagem , String usuario , String senha) throws Exception
    {
        return this.enviarSMS(ddd, celular, mensagem, usuario, senha, null);
    }

    /**
     * Enviar SMS com o SenderID (requer cadastro)
     * @param ddd
     * @param celular
     * @param mensagem
     * @param usuario
     * @param senha
     * @param senderid
     * @return Situação
     * @throws Exception
     */
    public String enviarSMS(String ddd , String celular , String mensagem , String usuario , String senha, String senderid) throws Exception
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ddd", ddd);
        params.put("celular", celular);
        params.put("mensagem", mensagem);
        params.put("usuario", usuario);
        params.put("senha", senha);
        if (senderid != null)
        {
            params.put("senderid", senderid);
        }

        return this.executeWebService(SMSService.SERVICE, "enviarsms2", params);
    }

    /**
     * Enviar uma lista de SMS para serem enviados por vez (máximo 50).
     * @param lista
     * @param mensagem
     * @param usuario
     * @param senha
     * @return Situação
     * @throws Exception
     */
    public String enviarListaSMS(ArrayList lista , String mensagem , String usuario , String senha) throws Exception
    {
        return this.enviarListaSMS(lista, mensagem, usuario, senha, null);
    }

    /**
     * Enviar uma lista de SMS para serem enviados por vez (máximo 50) com SenderID (requer cadastro).
     * @param lista
     * @param mensagem
     * @param usuario
     * @param senha
     * @param senderid
     * @return
     * @throws Exception
     */
    public String enviarListaSMS(ArrayList lista , String mensagem , String usuario , String senha, String senderid) throws Exception
    {
        HashMap<String, String> params = new HashMap<String, String>();
        String strLista = "";
        for(Object telefone : lista)
        {
            strLista += (strLista.length() > 0 ? "," : "") + telefone.toString();
        }
        params.put("lista", strLista);
        params.put("mensagem", mensagem);
        params.put("usuario", usuario);
        params.put("senha", senha);
        if (senderid != null)
        {
            params.put("senderid", senderid);
        }

        return this.executeWebService(SMSService.SERVICE, "enviarlistasms", params);
    }

    /**
     * Consulta Créditos
     * @param usuario
     * @param senha
     * @return Quantidade de Créditos
     * @throws Exception
     */
    public String creditos(String usuario , String senha) throws Exception
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("usuario", usuario);
        params.put("senha", senha);

        return this.executeWebService(SMSService.SERVICE, "creditos", params);
    }

}

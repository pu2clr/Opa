/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.ops.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rcaratti
 */
@Entity
@Table(name = "all_services", catalog = "caratti01", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AllServices.findAll", query = "SELECT a FROM AllServices a"),
    @NamedQuery(name = "AllServices.findByServiceId", query = "SELECT a FROM AllServices a WHERE a.serviceId = :serviceId"),
    @NamedQuery(name = "AllServices.findByServiceType", query = "SELECT a FROM AllServices a WHERE a.serviceType = :serviceType"),
    @NamedQuery(name = "AllServices.findByServiceName", query = "SELECT a FROM AllServices a WHERE a.serviceName = :serviceName"),
    @NamedQuery(name = "AllServices.findByQtdCases", query = "SELECT a FROM AllServices a WHERE a.qtdCases = :qtdCases")})
public class AllServices implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "service_id", nullable = false)
    @Id
    private int serviceId;
    @Size(max = 20)
    @Column(name = "service_type", length = 20)
    private String serviceType;
    @Size(max = 45)
    @Column(name = "service_name", length = 45)
    private String serviceName;
    @Column(name = "qtd_cases")
    private Integer qtdCases;

    public AllServices() {
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getQtdCases() {
        return qtdCases;
    }

    public void setQtdCases(Integer qtdCases) {
        this.qtdCases = qtdCases;
    }
    
}

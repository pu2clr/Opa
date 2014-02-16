/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.ops.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "service", catalog = "caratti01", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findByServiceId", query = "SELECT s FROM Service s WHERE s.serviceId = :serviceId"),
    @NamedQuery(name = "Service.findByServiceType", query = "SELECT s FROM Service s WHERE s.serviceType = :serviceType"),
    @NamedQuery(name = "Service.findByServiceName", query = "SELECT s FROM Service s WHERE s.serviceName = :serviceName"),
    @NamedQuery(name = "Service.findByDescription", query = "SELECT s FROM Service s WHERE s.description = :description"),
    @NamedQuery(name = "Service.findByQtdCases", query = "SELECT s FROM Service s WHERE s.qtdCases = :qtdCases"),
    @NamedQuery(name = "Service.findByIconName", query = "SELECT s FROM Service s WHERE s.iconName = :iconName")})
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;
    @Size(max = 20)
    @Column(name = "service_type", length = 20)
    private String serviceType;
    @Size(max = 45)
    @Column(name = "service_name", length = 45)
    private String serviceName;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "qtd_cases")
    private Integer qtdCases;
    @Size(max = 32)
    @Column(name = "icon_name", length = 32)
    private String iconName;

    public Service() {
    }

    public Service(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQtdCases() {
        return qtdCases;
    }

    public void setQtdCases(Integer qtdCases) {
        this.qtdCases = qtdCases;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.eti.caratti.ops.entity.Service[ serviceId=" + serviceId + " ]";
    }
    
}

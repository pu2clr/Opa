/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.ops.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rcaratti
 */
@Entity
@Table(name = "occurrence", catalog = "caratti01", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Occurrence.findAll", query = "SELECT o FROM Occurrence o"),
    @NamedQuery(name = "Occurrence.findById", query = "SELECT o FROM Occurrence o WHERE o.id = :id"),
    @NamedQuery(name = "Occurrence.findByDeviceId", query = "SELECT o FROM Occurrence o WHERE o.deviceId = :deviceId"),
    @NamedQuery(name = "Occurrence.findByDatetime", query = "SELECT o FROM Occurrence o WHERE o.datetime = :datetime"),
    @NamedQuery(name = "Occurrence.findByStatus", query = "SELECT o FROM Occurrence o WHERE o.status = :status"),
    @NamedQuery(name = "Occurrence.findByServiceProviderId", query = "SELECT o FROM Occurrence o WHERE o.serviceProviderId = :serviceProviderId"),
    @NamedQuery(name = "Occurrence.findByLatitude", query = "SELECT o FROM Occurrence o WHERE o.latitude = :latitude"),
    @NamedQuery(name = "Occurrence.findByLongitude", query = "SELECT o FROM Occurrence o WHERE o.longitude = :longitude"),
    @NamedQuery(name = "Occurrence.findByPhoneNumber", query = "SELECT o FROM Occurrence o WHERE o.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Occurrence.findByServiceId", query = "SELECT o FROM Occurrence o WHERE o.serviceId = :serviceId"),
    @NamedQuery(name = "Occurrence.findByMessage", query = "SELECT o FROM Occurrence o WHERE o.message = :message")})
public class Occurrence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 45)
    @Column(name = "device_id", length = 45)
    private String deviceId;
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "service_provider_id")
    private Integer serviceProviderId;
    @Size(max = 20)
    @Column(name = "latitude", length = 20)
    private String latitude;
    @Size(max = 20)
    @Column(name = "longitude", length = 20)
    private String longitude;
    @Size(max = 20)
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "service_id")
    private Integer serviceId;
    @Size(max = 140)
    @Column(name = "message", length = 140)
    private String message;

    public Occurrence() {
    }

    public Occurrence(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Occurrence)) {
            return false;
        }
        Occurrence other = (Occurrence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.eti.caratti.ops.entity.Occurrence[ id=" + id + " ]";
    }
    
}

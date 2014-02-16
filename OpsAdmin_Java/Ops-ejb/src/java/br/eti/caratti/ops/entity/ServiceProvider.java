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
@Table(name = "service_provider", catalog = "caratti01", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiceProvider.findAll", query = "SELECT s FROM ServiceProvider s"),
    @NamedQuery(name = "ServiceProvider.findByServiceProviderId", query = "SELECT s FROM ServiceProvider s WHERE s.serviceProviderId = :serviceProviderId"),
    @NamedQuery(name = "ServiceProvider.findByServiceId", query = "SELECT s FROM ServiceProvider s WHERE s.serviceId = :serviceId"),
    @NamedQuery(name = "ServiceProvider.findByName", query = "SELECT s FROM ServiceProvider s WHERE s.name = :name"),
    @NamedQuery(name = "ServiceProvider.findByEmail", query = "SELECT s FROM ServiceProvider s WHERE s.email = :email"),
    @NamedQuery(name = "ServiceProvider.findBySmsDdd", query = "SELECT s FROM ServiceProvider s WHERE s.smsDdd = :smsDdd"),
    @NamedQuery(name = "ServiceProvider.findBySmsNumber", query = "SELECT s FROM ServiceProvider s WHERE s.smsNumber = :smsNumber"),
    @NamedQuery(name = "ServiceProvider.findByPhoneNumber", query = "SELECT s FROM ServiceProvider s WHERE s.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "ServiceProvider.findBySendSms", query = "SELECT s FROM ServiceProvider s WHERE s.sendSms = :sendSms"),
    @NamedQuery(name = "ServiceProvider.findBySendEmail", query = "SELECT s FROM ServiceProvider s WHERE s.sendEmail = :sendEmail"),
    @NamedQuery(name = "ServiceProvider.findBySendPush", query = "SELECT s FROM ServiceProvider s WHERE s.sendPush = :sendPush"),
    @NamedQuery(name = "ServiceProvider.findByPositiveQualifies", query = "SELECT s FROM ServiceProvider s WHERE s.positiveQualifies = :positiveQualifies"),
    @NamedQuery(name = "ServiceProvider.findByNegativeQualifies", query = "SELECT s FROM ServiceProvider s WHERE s.negativeQualifies = :negativeQualifies"),
    @NamedQuery(name = "ServiceProvider.findByBusy", query = "SELECT s FROM ServiceProvider s WHERE s.busy = :busy"),
    @NamedQuery(name = "ServiceProvider.findByCanBeBusy", query = "SELECT s FROM ServiceProvider s WHERE s.canBeBusy = :canBeBusy")})
public class ServiceProvider implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "service_provider_id", nullable = false)
    private Integer serviceProviderId;
    @Column(name = "service_id")
    private Integer serviceId;
    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email", length = 45)
    private String email;
    @Size(max = 2)
    @Column(name = "sms_ddd", length = 2)
    private String smsDdd;
    @Size(max = 20)
    @Column(name = "sms_number", length = 20)
    private String smsNumber;
    @Size(max = 20)
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "send_sms")
    private Boolean sendSms;
    @Column(name = "send_email")
    private Boolean sendEmail;
    @Column(name = "send_push")
    private Boolean sendPush;
    @Column(name = "positive_qualifies")
    private Integer positiveQualifies;
    @Column(name = "negative_qualifies")
    private Integer negativeQualifies;
    @Column(name = "busy")
    private Boolean busy;
    @Column(name = "can_be_busy")
    private Boolean canBeBusy;

    public ServiceProvider() {
    }

    public ServiceProvider(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmsDdd() {
        return smsDdd;
    }

    public void setSmsDdd(String smsDdd) {
        this.smsDdd = smsDdd;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getSendSms() {
        return sendSms;
    }

    public void setSendSms(Boolean sendSms) {
        this.sendSms = sendSms;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getSendPush() {
        return sendPush;
    }

    public void setSendPush(Boolean sendPush) {
        this.sendPush = sendPush;
    }

    public Integer getPositiveQualifies() {
        return positiveQualifies;
    }

    public void setPositiveQualifies(Integer positiveQualifies) {
        this.positiveQualifies = positiveQualifies;
    }

    public Integer getNegativeQualifies() {
        return negativeQualifies;
    }

    public void setNegativeQualifies(Integer negativeQualifies) {
        this.negativeQualifies = negativeQualifies;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Boolean getCanBeBusy() {
        return canBeBusy;
    }

    public void setCanBeBusy(Boolean canBeBusy) {
        this.canBeBusy = canBeBusy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceProviderId != null ? serviceProviderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceProvider)) {
            return false;
        }
        ServiceProvider other = (ServiceProvider) object;
        if ((this.serviceProviderId == null && other.serviceProviderId != null) || (this.serviceProviderId != null && !this.serviceProviderId.equals(other.serviceProviderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.eti.caratti.ops.entity.ServiceProvider[ serviceProviderId=" + serviceProviderId + " ]";
    }
    
}

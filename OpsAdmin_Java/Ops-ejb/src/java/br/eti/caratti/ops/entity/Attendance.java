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
@Table(name = "attendance", catalog = "caratti01", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findByAttendanceId", query = "SELECT a FROM Attendance a WHERE a.attendanceId = :attendanceId"),
    @NamedQuery(name = "Attendance.findByOccurrenceId", query = "SELECT a FROM Attendance a WHERE a.occurrenceId = :occurrenceId"),
    @NamedQuery(name = "Attendance.findByDatetime", query = "SELECT a FROM Attendance a WHERE a.datetime = :datetime"),
    @NamedQuery(name = "Attendance.findByStatus", query = "SELECT a FROM Attendance a WHERE a.status = :status")})
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "attendance_id", nullable = false)
    private Integer attendanceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "occurrence_id", nullable = false)
    private int occurrenceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    public Attendance() {
    }

    public Attendance(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Attendance(Integer attendanceId, int occurrenceId, Date datetime) {
        this.attendanceId = attendanceId;
        this.occurrenceId = occurrenceId;
        this.datetime = datetime;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(int occurrenceId) {
        this.occurrenceId = occurrenceId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendanceId == null && other.attendanceId != null) || (this.attendanceId != null && !this.attendanceId.equals(other.attendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.eti.caratti.ops.entity.Attendance[ attendanceId=" + attendanceId + " ]";
    }
    
}

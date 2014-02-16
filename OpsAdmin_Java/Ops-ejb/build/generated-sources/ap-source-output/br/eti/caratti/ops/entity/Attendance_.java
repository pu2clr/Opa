package br.eti.caratti.ops.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-11-03T12:55:30")
@StaticMetamodel(Attendance.class)
public class Attendance_ { 

    public static volatile SingularAttribute<Attendance, Integer> attendanceId;
    public static volatile SingularAttribute<Attendance, String> status;
    public static volatile SingularAttribute<Attendance, Integer> occurrenceId;
    public static volatile SingularAttribute<Attendance, Date> datetime;

}
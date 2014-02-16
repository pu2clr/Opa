package br.eti.caratti.ops.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-11-03T12:55:30")
@StaticMetamodel(Occurrence.class)
public class Occurrence_ { 

    public static volatile SingularAttribute<Occurrence, Integer> id;
    public static volatile SingularAttribute<Occurrence, String> message;
    public static volatile SingularAttribute<Occurrence, String> phoneNumber;
    public static volatile SingularAttribute<Occurrence, Integer> serviceId;
    public static volatile SingularAttribute<Occurrence, String> status;
    public static volatile SingularAttribute<Occurrence, String> longitude;
    public static volatile SingularAttribute<Occurrence, String> latitude;
    public static volatile SingularAttribute<Occurrence, Date> datetime;
    public static volatile SingularAttribute<Occurrence, Integer> serviceProviderId;
    public static volatile SingularAttribute<Occurrence, String> deviceId;

}
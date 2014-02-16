package br.eti.caratti.ops.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2011-11-03T12:55:30")
@StaticMetamodel(ServiceProvider.class)
public class ServiceProvider_ { 

    public static volatile SingularAttribute<ServiceProvider, Integer> positiveQualifies;
    public static volatile SingularAttribute<ServiceProvider, String> smsNumber;
    public static volatile SingularAttribute<ServiceProvider, Boolean> sendPush;
    public static volatile SingularAttribute<ServiceProvider, Boolean> busy;
    public static volatile SingularAttribute<ServiceProvider, Integer> serviceProviderId;
    public static volatile SingularAttribute<ServiceProvider, String> smsDdd;
    public static volatile SingularAttribute<ServiceProvider, Integer> serviceId;
    public static volatile SingularAttribute<ServiceProvider, String> phoneNumber;
    public static volatile SingularAttribute<ServiceProvider, String> email;
    public static volatile SingularAttribute<ServiceProvider, String> name;
    public static volatile SingularAttribute<ServiceProvider, Boolean> canBeBusy;
    public static volatile SingularAttribute<ServiceProvider, Boolean> sendEmail;
    public static volatile SingularAttribute<ServiceProvider, Integer> negativeQualifies;
    public static volatile SingularAttribute<ServiceProvider, Boolean> sendSms;

}
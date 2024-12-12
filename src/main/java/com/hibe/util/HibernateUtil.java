package com.hibe.util;

import com.hibe.converter.BirthdayConverter;
import com.hibe.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        return configuration.buildSessionFactory();
    }
}

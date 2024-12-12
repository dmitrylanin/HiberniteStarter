package ru.springFast;

import com.hibe.converter.BirthdayConverter;
import com.hibe.entity.*;
import com.hibe.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @Test
    public void checkOneToOne() {
        //@Cleanup нужна для автоматического вызова метода close()
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        Company newMutants = Company.builder()
                .name("New Mutants2")
                .build();

        User blackWidow = User.builder()
                .username("blackWidow2@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Scarlett")
                        .lastname("Johansson")
                        .birthday(new Birthday(LocalDate.of(1984, 11, 22)))
                        .build())
                .role(Role.ADMIN)
                .company(newMutants)
                .build();

        Profile blackWidowProfile = Profile.builder()
                .language("RU")
                .street("Wall Street")
                .build();

        session.save(newMutants);
        session.save(blackWidow);
        blackWidowProfile.setUser(blackWidow);
        session.save(blackWidowProfile);

        session.getTransaction().commit();
    }



    public void checkOrphalRemoval(){
        //@Cleanup нужна для автоматического вызова метода close()
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 8);
        company.getUsers().removeIf(user -> user.getId().equals(23L));

        session.getTransaction().commit();
    }

    public void addNewUserAndCompany(){
        //@Cleanup нужна для автоматического вызова метода close()
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        Company fantastic4 = Company.builder()
                .name("Fantastic 4")
                .build();

        User susanStorm = User.builder()
                .username("sue@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Jessica")
                        .lastname("Alba")
                        .birthday(new Birthday(LocalDate.of(1981, 4, 28)))
                        .build())
                .role(Role.ADMIN)
                .company(fantastic4)
                .build();

        fantastic4.addUser(susanStorm);
        session.save(fantastic4);
        session.getTransaction().commit();
    }


    public void checkOneToMany(){
        //@Cleanup нужна для автоматического вызова метода close()
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 8);
        System.out.println(company.getUsers());
        session.getTransaction().commit();
    }



    public void testHibernateApi() throws SQLException, IllegalAccessException {
        Company newMutants = Company.builder()
                .name("New Mutants")
                .build();

        User user = User.builder()
                .username("blackWidow1@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Scarlett")
                        .lastname("Johansson")
                        .birthday(new Birthday(LocalDate.of(1984, 11, 22)))
                        .build())
                .role(Role.ADMIN)
                .company(newMutants)
                .build();

        var sql = """
                    insert into %s (%s) values (%s)
                """;

        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + "." + table.name())
                .orElse(user.getClass().getName());

        Field[] fields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName())
                ).collect(Collectors.joining(", "));

        var columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hibernite_starter",
                "postgres", "postgres");

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql.formatted(tableName, columnNames, columnValues));


        for (int i=0; i<fields.length; i++){
            //разрешить доступ к private-полям
            fields[i].setAccessible(true);
            if(i==3){
                preparedStatement.setDate(i+1,
                        (((new BirthdayConverter()).convertToDatabaseColumn((Birthday) fields[i].get(user)))));
            }else if(i==6){
                preparedStatement.setObject(i+1, fields[i].get(user).toString());
            }else{
                preparedStatement.setObject(i+1, fields[i].get(user));
            }
        }

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
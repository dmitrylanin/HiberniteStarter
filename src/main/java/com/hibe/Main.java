package com.hibe;

import com.hibe.entity.*;
import com.hibe.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
            try (Session session = sessionFactory.openSession()){
                /*
                Company newMutants = Company.builder()
                        .name("New Mutants")
                        .build();

                User blackWidow = User.builder()
                        .username("blackWidow1@mail.ru")
                        .personalInfo(PersonalInfo.builder()
                                .firstname("Scarlett")
                                .lastname("Johansson")
                                .birthday(new Birthday(LocalDate.of(1984, 11, 22)))
                                .build())
                        .role(Role.ADMIN)
                        .company(newMutants)
                        .build();
                 */

                Company newMutants = session.get(Company.class, 8);

                User magik = User.builder()
                        .username("magik@mail.ru")
                        .personalInfo(PersonalInfo.builder()
                                .firstname("Anya")
                                .lastname("Taylor-Joy")
                                .birthday(new Birthday(LocalDate.of(1996, 4, 16)))
                                .build())
                        .role(Role.ADMIN)
                        .company(newMutants)
                        .build();

                session.beginTransaction();
                session.saveOrUpdate(magik);
                session.getTransaction().commit();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void saveUser(User user){
        log.info("User object: {} - in transient state", user);

        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory()){
            try(Session session1 = sessionFactory.openSession()){
                //User в статусе Persistence для session1
                //и одновременно Transient для session2
                session1.beginTransaction();

                //ВАЖНО: user зависит от company, поэтому сперва нужно сохранить company
                //при попытке использовать метод merge данные НЕ записались в БД

                //session1.saveOrUpdate(company);
                session1.saveOrUpdate(user);
                //log.warn("Transaction with object: {} - yet hasn't commited", user);
                session1.getTransaction().commit();
            }
        /*
            try(Session session2 = sessionFactory.openSession()){
        //Сначала Hibernite выполнит get, получит user и сделает его Persistence
        //в результате, user станет DETACHED по отношению к session1
                session2.beginTransaction();
                session2.delete(user);

        //User становится REMOVED к session2
                session2.getTransaction().commit();
            }
        */
        }catch(Exception e){
            log.error("Error occurred: {} with object: {}", e.getMessage(), user);
        }
    }

    public void deleteUser(){
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory()){
            try(Session session = sessionFactory.openSession()){
                var user = session.get(User.class, 9L);

                session.beginTransaction();
                log.info("Try to remove object: {}", user);
                session.delete(user);
                session.getTransaction().commit();
                log.info("Success remove object: {}", user);
            }
        }catch(Exception e){
            //log.error("Error occurred while removing: {} with object: {}", e.getMessage(), user);
        }
    }
}
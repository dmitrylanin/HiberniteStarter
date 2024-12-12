package com.hibe.util;

import com.hibe.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataLists {

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Company> companies = getCompanies();

        User magik = User.builder()
                .username("magik@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Anya")
                        .lastname("Taylor-Joy")
                        .birthday(new Birthday(LocalDate.of(1996, 4, 16)))
                        .build())
                .role(Role.ADMIN)
                .company(companies.get(0))
                .build();

        User blackWidow = User.builder()
                .username("blackWidow@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Scarlett")
                        .lastname("Johansson")
                        .birthday(new Birthday(LocalDate.of(1984, 11, 22)))
                        .build())
                .role(Role.ADMIN)
                .company(companies.get(1))
                .build();

        User susanStorm = User.builder()
                .username("sue@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Jessica")
                        .lastname("Alba")
                        .birthday(new Birthday(LocalDate.of(1981, 4, 28)))
                        .build())
                .role(Role.ADMIN)
                .company(companies.get(2))
                .build();

        User mystique = User.builder()
                .username("mystique@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Jennifer")
                        .lastname("Lawrence")
                        .birthday(new Birthday(LocalDate.of(1990, 8, 15)))
                        .build())
                .role(Role.ADMIN)
                .company(companies.get(3))
                .build();

        User jeanGrey = User.builder()
                .username("jeanGrey@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Famke")
                        .lastname("Janssen")
                        .birthday(new Birthday(LocalDate.of(1964, 11, 5)))
                        .build())
                .role(Role.ADMIN)
                .company(companies.get(3))
                .build();

        User harleyQuinn = User.builder()
                .username("harleyQuinn@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Harley")
                        .lastname("Quinn")
                        .birthday(new Birthday(LocalDate.of(1993, 9, 12)))
                        .build())
                .role(Role.ADMIN)
                .build();

        users.add(magik);
        users.add(blackWidow);
        users.add(susanStorm);
        users.add(mystique);
        users.add(jeanGrey);
        users.add(harleyQuinn);

        return users;
    }


    public ArrayList<Company> getCompanies(){
        ArrayList<Company> companies = new ArrayList<>();

        Company newMutants = Company.builder()
                .name("New Mutants")
                .build();

        Company avengers = Company.builder()
                .name("Avengers")
                .build();

        Company fantasticFour = Company.builder()
                .name("Fantastic Four")
                .build();

        Company xMens = Company.builder()
                .name("X mens")
                .build();

        companies.add(newMutants);
        companies.add(avengers);
        companies.add(fantasticFour);
        companies.add(xMens);

        return companies;
    }

}

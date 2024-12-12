package com.hibe.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"company", "profile"})
/*
    По аналогии с Company переопределяем базу для расчета метода EqualsAndHashCode:
    используем для этого только уникальный username
 */
@EqualsAndHashCode(of="username")
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "user_gen", sequenceName = "my_users_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Embedded
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}
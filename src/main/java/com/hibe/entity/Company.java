package com.hibe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
    чтобы использовать коллекцию НЕ List, а Set переопределяем список полей,
    по которым рассчитываются хэши. Теперь для Company хэш рассчитывается только по name,
    потому что это поле unique = true, nullable = false
 */
@EqualsAndHashCode(of = "name")
@Table(name = "companies", schema = "public")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", orphanRemoval = true )
    public Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
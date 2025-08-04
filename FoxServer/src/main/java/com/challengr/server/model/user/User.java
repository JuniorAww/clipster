package com.challengr.server.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    @Column(nullable = false)
    private List<Credential> credentials;

    @Column
    private List<GrantedAuthority> authorities;
}

package com.devkh.onlinestore.api.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(unique = true, length = 80, nullable = false)
    private String username;
    @Column(unique = true, length = 80, nullable = false)
    private String email;
    @Column(name = "encrypted_passwd", nullable = false)
    private String password;
    @Column(length = 30)
    private String nickName;
    @Column(length = 10)
    private String verifiedCode;
    private Boolean isVerified;
    private Boolean isDeleted;
    @ManyToMany
    @JoinTable(joinColumns =
        @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}

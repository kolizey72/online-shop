package com.github.kolizey72.onlineshop.entity;

import com.github.kolizey72.onlineshop.dto.UserRegistrationForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Size(min = 3, max = 32, message = "Username length must be 3-32")
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    @Getter @Setter
    private String email;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserClass userClass;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserProfession userProfession;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Birthday can not be in the future")
    @Getter @Setter
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date registration;

    @Getter @Setter
    private String role;

    @Getter @Setter
    private Boolean banned;

    @OneToMany(mappedBy = "seller")
    @Getter @Setter
    private List<Product> products;

    public User(UserRegistrationForm user) {
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        userClass = user.getUserClass();
        userProfession = user.getUserProfession();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

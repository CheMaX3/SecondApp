package project.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Size(min = 4, max = 255, message = "Имя пользователя не может быть короче 4 символов")
    @Column (name = "username")
    private String username;

    @NotEmpty(message = "Поле имя не может быть пустым")
    @Size(min = 3, max = 255)
    @Column (name = "firstName")
    private String firstName;

    @NotEmpty(message = "Поле фамилия не может быть пустым")
    @Size(min = 2, max = 255)
    @Column (name = "lastName")
    private String lastName;

    @Size(min = 4, max = 255)
    @Column (name = "middleName")
    private String middleName;

    @Size(min = 4, max = 255)
    @Email
    @Column (name = "email")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})", message = "Номер телефона должен содержать 10 цифр")
    @Column (name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @NotEmpty(message = "Поле пароля не может быть пустым")
    @Size(min = 4, max = 255)
    @Column (name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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

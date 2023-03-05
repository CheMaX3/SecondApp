package project.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.entity.Role;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Data
public class UserProfile implements UserDetails {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private Integer phoneNumber;
    private String password;
    private Set<Role> roles;
    private BigDecimal balance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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

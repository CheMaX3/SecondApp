package project.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table (name = "role")
public class Role implements GrantedAuthority {

    @Id
    private Integer id;
    private String name;

    @Transient
    @ManyToMany (mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}


package com.senlainc.advertisementsystem.model.user.role.permission;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.user.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "permissions")
@Data
public class Permission extends AbstractModel implements GrantedAuthority {

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @Override
    public String getAuthority() {
        return "OP_" + name;
    }
}

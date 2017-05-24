package nikita.model.noark5.v4.interfaces.entities.admin;

import nikita.model.noark5.v4.admin.Authority;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by tsodring on 5/23/17.
 */
public interface IUserEntity extends INikitaEntity {


    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getFirstname();

    void setFirstname(String firstname);

    String getLastname();

    void setLastname(String lastname);

    String getEmail();

    void setEmail(String email);

    Boolean getEnabled();

    void setEnabled(Boolean enabled);


    boolean isAccountNonExpired();

    void setAccountNonExpired(boolean accountNonExpired);

    boolean isCredentialsNonExpired();

    void setCredentialsNonExpired(boolean credentialsNonExpired);

    boolean isAccountNonLocked();

    void setAccountNonLocked(boolean accountNonLocked);

    List<Authority> getAuthorities();

    void setAuthorities(List<Authority> authorities);

    Date getLastPasswordResetDate();

    void setLastPasswordResetDate(Date lastPasswordResetDate);
}

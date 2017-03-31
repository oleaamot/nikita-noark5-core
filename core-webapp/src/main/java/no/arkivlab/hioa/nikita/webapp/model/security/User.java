package no.arkivlab.hioa.nikita.webapp.model.security;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nikita_user")
public class User {

    @Column(name = "account_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date createdDate;
    @NotNull
    @Column(name = "account_non_expired", nullable = false)
    boolean accountNonExpired;
    @NotNull
    @Column(name = "credentials_non_expired", nullable = false)
    boolean credentialsNonExpired;
    @NotNull
    @Column(name = "account_non_locked", nullable = false)
    boolean accountNonLocked;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column(name = "username", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;
    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;
    @Column(name = "firstname", length = 50)
    @Size(min = 4, max = 50)
    private String firstname;
    @Column(name = "lastname", length = 50)
    @Size(min = 4, max = 50)
    private String lastname;
    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;
    @Column(name = "enabled")
    @NotNull
    private Boolean enabled;
    @Column(name = "last_password_reset_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;
    @Column(name = "lang_key", length = 5)
    private String langKey;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nikita_user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}
package com.jingrui.jrap.security.ldap;

import java.util.Arrays;  
import java.util.Collection;  
import org.springframework.ldap.core.DirContextOperations;  
import org.springframework.security.core.GrantedAuthority;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

/**
 * @author qixiangyu
 */
public class SimpleRoleGrantingLdapAuthoritiesPopulator implements  
        LdapAuthoritiesPopulator {

    protected String role = "ROLE_USER";  

    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(  
            DirContextOperations userData, String username) {  
        GrantedAuthority ga = new SimpleGrantedAuthority(role);  
        return Arrays.asList(ga);  
    }  
  
    public String getRole() {  
        return role;  
    }  
  
    public void setRole(String role) {  
        this.role = role;  
    }  
}  
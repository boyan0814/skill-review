package com.example.keycloak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 處理JWT Role默認會有"ROLE_"前墜問題
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;
    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractResourceRoles(jwt).stream()).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt) //沒用到
        );
    }

    //如果id不是用sub存，可以改
    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if(principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        /*
        "resource_access": {
            "boyen-rest-api": {
                "roles": [
                "client_admin"
      ]
            },
            "account": {
                "roles": [
                "manage-account",
                        "manage-account-links",
                        "view-profile"
      ]
            }
        }
        */
        if(jwt.getClaim("resource_access") == null) {
            return Set.of();
        }

        resourceAccess = jwt.getClaim("resource_access"); //boyen-rest-api
        if(resourceAccess.get(resourceId) == null) {
            return Set.of();
        }

        resource = (Map<String, Object>)resourceAccess.get(resourceId);
        resourceRoles = (Collection<String>) resource.get("roles");//client-admin

        //加"ROLE_"前墜
        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}

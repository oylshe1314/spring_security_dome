package com.example.dome.application.auth;

import com.example.dome.application.entity.RoleApiRelation;
import com.example.dome.application.service.RoleApiRelationService;
import com.example.dome.application.util.IterateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Component
public class UserRoleSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleApiRelationService roleApiRelationService;

    @Override
    public List<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String uri = request.getRequestURI();
        List<RoleApiRelation> relations = roleApiRelationService.getAllByPath(uri);
        if(relations.isEmpty()) {
            return null;
        }
        return IterateUtils.convert(relations, relation -> new SecurityConfig(relation.role.name));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<RoleApiRelation> relations = roleApiRelationService.getAll();
        if(relations.isEmpty()) {
            return null;
        }
        return IterateUtils.convert(relations, relation -> new SecurityConfig(relation.role.name));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

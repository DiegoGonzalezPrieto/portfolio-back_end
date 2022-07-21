package com.argentinaprograma.portfolio.security.method;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;


public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler  {

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, org.aopalliance.intercept.MethodInvocation invocation) {
        System.out.println("creating security expression root");
        CustomMethodSecurityExpressionRoot root = 
          new CustomMethodSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root; 
    }

    private AuthenticationTrustResolver trustResolver = 
        new AuthenticationTrustResolverImpl();
}
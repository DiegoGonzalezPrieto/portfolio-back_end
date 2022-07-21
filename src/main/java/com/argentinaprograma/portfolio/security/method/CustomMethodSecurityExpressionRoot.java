package com.argentinaprograma.portfolio.security.method;

import com.argentinaprograma.portfolio.model.Person;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


public class CustomMethodSecurityExpressionRoot extends 
        SecurityExpressionRoot implements MethodSecurityExpressionOperations {


    private Object filterObject;
    private Object returnObject;
    
    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
    
    public boolean isPerson(Long personId) {

        UserSecurity user = ((UserSecurity) this.getPrincipal());
        Person person = user.getPerson();
        return personId == person.getId();
    }

	public boolean isAdmin(){{
        UserSecurity user = ((UserSecurity) this.getPrincipal());
        Person person = user.getPerson();
		System.out.println("########");
		System.out.println(person.getId());
        return person.getId() == 1;
	}}
    
    
    
    @Override
    public Object getThis() {
        return this;
    }
    
    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }
}

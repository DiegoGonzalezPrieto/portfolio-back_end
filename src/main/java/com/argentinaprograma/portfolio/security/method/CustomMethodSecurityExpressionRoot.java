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
    
    
    /*
    
    // method doesn't work because 
    // access to complete Person object seems to be unavailable
    
    public boolean isPersonElement(String attr, Long id) {
        
        System.out.println("##############\nisPersonElement(attr, id): checking...");
        
        UserSecurity user = ((UserSecurity) this.getPrincipal());
        Person person = user.getPerson();
        
        // gets proxies....
        List<? extends PersonElement> attributeList = person.getByAttributeName(attr);
        System.out.println("###############\ngot attributes OK");
        for (PersonElement personElement : attributeList) {
            if (personElement.getId() == id){
                System.out.println("###\n###\n####\nchecked and returned true");
                return true;
            } 
        }
        return false;
    }
    */
    
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

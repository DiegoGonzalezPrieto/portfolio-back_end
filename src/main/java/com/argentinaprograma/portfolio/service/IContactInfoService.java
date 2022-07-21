package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.ContactInfo;
import java.util.List;

public interface IContactInfoService {

    // get all contactInfo
    public List<ContactInfo> getContactInfos();

    // create contactInfo
    public ContactInfo saveContactInfo(ContactInfo contactInfo);

    // delete contactInfo
    public boolean deleteContactInfo(Long id);

    // find contactInfo
    public ContactInfo findContactInfo(Long id);

    // find all ContactInfo by personId
    public List<ContactInfo> getContactInfosByPersonID(Long personId);

}

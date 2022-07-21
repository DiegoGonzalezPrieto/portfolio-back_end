package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.ContactInfo;
import com.argentinaprograma.portfolio.repository.IContactInfoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService implements IContactInfoService {

    @Autowired
    private IContactInfoRepo cInfoRepo;

    @Override
    public List<ContactInfo> getContactInfos() {
        List<ContactInfo> ci = cInfoRepo.findAll();
        return ci;
    }

    @Override
    public ContactInfo saveContactInfo(ContactInfo cInfo) {
        return cInfoRepo.save(cInfo);
    }

    @Override
    public boolean deleteContactInfo(Long id) {
        try {
            cInfoRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    @Override
    public ContactInfo findContactInfo(Long id) {
        ContactInfo ci = cInfoRepo.findById(id).orElse(null);
        return ci;
    }

    @Override
    public List<ContactInfo> getContactInfosByPersonID(Long personId) {
        return cInfoRepo.findAllByPersonId(personId);
    }

}

package com.argentinaprograma.portfolio.service;



import com.argentinaprograma.portfolio.model.Language;
import com.argentinaprograma.portfolio.repository.ILanguageRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService implements ILanguageService {

    @Autowired
    private ILanguageRepo langRepo;

    @Override
    public List<Language> getLanguages() {
        List<Language> l = langRepo.findAll();
        return l;
    }

    @Override
    public Language saveLanguage(Language language) {
        return langRepo.save(language);
    }

    @Override
    public boolean deleteLanguage(Long id) {
        try {
            langRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Language findLanguage(Long id) {
        Language l = langRepo.findById(id).orElse(null);
        return l;
    }

    @Override
    public Language findByName(String name) {
        Language l = langRepo.findByName(name);
        return l;
    }

}

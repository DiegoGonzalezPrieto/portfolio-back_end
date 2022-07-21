package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.Language;
import java.util.List;

public interface ILanguageService {

    // get all language
    public List<Language> getLanguages();

    // create language
    public Language saveLanguage(Language language);

    // delete language
    public boolean deleteLanguage(Long id);

    // find language
    public Language findLanguage(Long id);
    
    // find language by name
    public Language findByName(String name);


}

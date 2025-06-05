package com.xgaslan.data.seeds;

import com.xgaslan.data.entities.City;
import com.xgaslan.data.entities.Country;
import com.xgaslan.data.entities.Currency;
import com.xgaslan.data.entities.Language;
import com.xgaslan.repositories.ICityRepository;
import com.xgaslan.repositories.ICountryRepository;
import com.xgaslan.repositories.ICurrencyRepository;
import com.xgaslan.repositories.ILanguageRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class ApplicationSeeder implements ApplicationRunner {

    private final ILanguageRepository _I_languageRepository;
    private final ICountryRepository _countryRepository;
    private final ICityRepository _cityRepository;
    private final ICurrencyRepository _currencyRepository;

    public ApplicationSeeder(ILanguageRepository ILanguageRepository, ICountryRepository countryRepository, ICityRepository cityRepository, ICurrencyRepository currencyRepository) {
        _I_languageRepository = ILanguageRepository;
        _countryRepository = countryRepository;
        _cityRepository = cityRepository;
        _currencyRepository = currencyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedLanguages();
        seedCurrencies();
        seedCountries();
        seedCities();
    }

    private void seedLanguages() {
        seedLanguage(new Language("TR", "Türkçe"));
        seedLanguage(new Language("EN", "English"));
    }

    private void seedLanguage(Language language) {
        if(!_I_languageRepository.existsById(language.getId())) {
            _I_languageRepository.save(language);
        }
    }

    private void seedCurrencies() {
        seedCurrency(new Currency("TL", "Türk Lirası"));
        seedCurrency(new Currency("USD", "United States Dollar"));
    }

    private void seedCurrency(Currency currency) {
        if(!_currencyRepository.existsById(currency.getId())) {
            _currencyRepository.save(currency);
        }
    }

    private void seedCountries() {
        seedCountry(new Country(90L, "Türkiye"));
    }

    private void seedCountry(Country country) {
        Optional<Country> existing = _countryRepository.findById(country.getId());
        if (existing.isPresent()) {
            Country dbCountry = existing.get();
            dbCountry.setName(country.getName());
            _countryRepository.save(dbCountry);
        }
    }

    private void seedCities() {
        seedCity(34L, "İstanbul", 90L);
        seedCity(35L, "İzmir", 90L);
    }

    private void seedCity(Long id, String name, Long countryId) {
        if(!_cityRepository.existsById(id)) {
            Optional<Country> country = _countryRepository.findById(countryId);

            City city = new City(id, name, country.orElse(null));
            _cityRepository.save(city);
        }
    }
}

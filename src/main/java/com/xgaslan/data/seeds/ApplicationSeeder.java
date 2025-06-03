package com.xgaslan.data.seeds;

import com.xgaslan.data.entities.City;
import com.xgaslan.data.entities.Country;
import com.xgaslan.data.entities.Currency;
import com.xgaslan.data.entities.Language;
import com.xgaslan.repositories.CityRepository;
import com.xgaslan.repositories.CountryRepository;
import com.xgaslan.repositories.CurrencyRepository;
import com.xgaslan.repositories.LanguageRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class ApplicationSeeder implements ApplicationRunner {

    private final LanguageRepository _languageRepository;
    private final CountryRepository _countryRepository;
    private final CityRepository _cityRepository;
    private final CurrencyRepository _currencyRepository;

    public ApplicationSeeder(LanguageRepository languageRepository, CountryRepository countryRepository, CityRepository cityRepository, CurrencyRepository currencyRepository) {
        _languageRepository = languageRepository;
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
        if(!_languageRepository.existsById(language.getId())) {
            _languageRepository.save(language);
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

package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.repository.AirplaneRepository;

@Service
public class AirplaneService {

    @Autowired
    protected AirplaneRepository airplaneRepository;

    @Transactional
    public Airplane getAirplane(Long id) {
        Optional<Airplane> result = this.airplaneRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Airplane saveAirplane(Airplane airplane) {
        return this.airplaneRepository.save(airplane);
    }

    @Transactional
    public List<Airplane> getAllAirplanes() {
        List<Airplane> result = new ArrayList<>();
        this.airplaneRepository.findAll().forEach(result::add);
        return result;
    }

    @Transactional
    public void deleteAirplane(Long id) {
        this.airplaneRepository.deleteById(id);
    }

    @Transactional
    public Airplane saveAirplaneWithCustomizations(
            Airplane airplane,
            List<String> names,
            List<String> descriptions,
            List<String> dates,
            List<Float> prices,
            List<String> urls) {

        if (names != null) {
            for (int i = 0; i < names.size(); i++) {
                AirplaneCustomization customization = new AirplaneCustomization();
                customization.setDescriptionName(names.get(i));
                customization.setDescription(descriptions.get(i));
                customization.setModificationFirstAvailabilityDate(LocalDate.parse(dates.get(i)));
                customization.setModificationPrice(prices.get(i));
                customization.setUrlImage(urls.get(i));

                airplane.getCustomizations().add(customization);
            }
        }

        return this.airplaneRepository.save(airplane);
    }

    @Transactional
    public void addCustomizationToAirplane(Long airplaneId, AirplaneCustomization customization) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
            .orElseThrow(() -> new IllegalArgumentException("Aereo non trovato"));

        if (customization.getModificationPrice() < 0)
            throw new IllegalArgumentException("Prezzo della modifica non valido");

        airplane.getCustomizations().add(customization);
        airplaneRepository.save(airplane);
    }
}

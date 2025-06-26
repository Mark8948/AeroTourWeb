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
        //return this.airplaneRepository.findWithCustomizations(id).orElse(null);
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
            List<Long> modificationIds,
            List<String> names,
            List<String> descriptions,
            List<String> dates,
            List<Float> prices) {

        Airplane airplaneToSave;
        if (airplane.getId() != null) {
            airplaneToSave = this.airplaneRepository.findById(airplane.getId())
                                .orElse(airplane);
            airplaneToSave.setModelName(airplane.getModelName());
            airplaneToSave.setDescription(airplane.getDescription());
            airplaneToSave.setBuildYear(airplane.getBuildYear());
            airplaneToSave.setPrice(airplane.getPrice());
            airplaneToSave.setImage(airplane.getImage());

            if (airplaneToSave.getCustomizations() == null) {
                airplaneToSave.setCustomizations(new ArrayList<>());
            }

            List<AirplaneCustomization> updatedCustomizations = new ArrayList<>();

            if (names != null) {
                for (int i = 0; i < names.size(); i++) {
                    AirplaneCustomization customization;

                    if (modificationIds != null && modificationIds.size() > i && modificationIds.get(i) != null) {
                        Long modId = modificationIds.get(i);
                        customization = airplaneToSave.getCustomizations().stream()
                            .filter(c -> c.getId() != null && c.getId().equals(modId))
                            .findFirst()
                            .orElse(new AirplaneCustomization());
                        customization.setId(modId);
                    } else {
                        customization = new AirplaneCustomization();
                    }

                    customization.setModificationName(names.get(i));
                    customization.setDescription(descriptions.get(i));
                    customization.setModificationFirstAvailabilityDate(LocalDate.parse(dates.get(i)));
                    customization.setModificationPrice(prices.get(i));
                    customization.setAirplane(airplaneToSave);

                    updatedCustomizations.add(customization);
                }
            }

            airplaneToSave.getCustomizations().clear();
            airplaneToSave.getCustomizations().addAll(updatedCustomizations);

        } else {
            airplaneToSave = airplane;

            if (names != null) {
                List<AirplaneCustomization> newCustomizations = new ArrayList<>();
                for (int i = 0; i < names.size(); i++) {
                    AirplaneCustomization customization = new AirplaneCustomization();
                    customization.setModificationName(names.get(i));
                    customization.setDescription(descriptions.get(i));
                    customization.setModificationFirstAvailabilityDate(LocalDate.parse(dates.get(i)));
                    customization.setModificationPrice(prices.get(i));
                    customization.setAirplane(airplaneToSave);
                    newCustomizations.add(customization);
                }
                airplaneToSave.setCustomizations(newCustomizations);
            }
        }

        return this.airplaneRepository.save(airplaneToSave);
    }

    @Transactional
    public void addCustomizationToAirplane(Long airplaneId, AirplaneCustomization customization) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
            .orElseThrow(() -> new IllegalArgumentException("Aereo non trovato"));

        if (customization.getModificationPrice() < 0)
            throw new IllegalArgumentException("Prezzo della modifica non valido");

        customization.setAirplane(airplane);
        airplane.getCustomizations().add(customization);
        airplaneRepository.save(airplane);
    }
}

package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.Cargo;
import com.medihelp.presupuesto.repository.CargoRepository;
import com.medihelp.presupuesto.service.CargoService;
import com.medihelp.presupuesto.service.dto.CargoDTO;
import com.medihelp.presupuesto.service.mapper.CargoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cargo}.
 */
@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private static final Logger LOG = LoggerFactory.getLogger(CargoServiceImpl.class);

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    public CargoServiceImpl(CargoRepository cargoRepository, CargoMapper cargoMapper) {
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
    }

    @Override
    public CargoDTO save(CargoDTO cargoDTO) {
        LOG.debug("Request to save Cargo : {}", cargoDTO);
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargo = cargoRepository.save(cargo);
        return cargoMapper.toDto(cargo);
    }

    @Override
    public CargoDTO update(CargoDTO cargoDTO) {
        LOG.debug("Request to update Cargo : {}", cargoDTO);
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargo = cargoRepository.save(cargo);
        return cargoMapper.toDto(cargo);
    }

    @Override
    public Optional<CargoDTO> partialUpdate(CargoDTO cargoDTO) {
        LOG.debug("Request to partially update Cargo : {}", cargoDTO);

        return cargoRepository
            .findById(cargoDTO.getId())
            .map(existingCargo -> {
                cargoMapper.partialUpdate(existingCargo, cargoDTO);

                return existingCargo;
            })
            .map(cargoRepository::save)
            .map(cargoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CargoDTO> findAll() {
        LOG.debug("Request to get all Cargos");
        return cargoRepository.findAll().stream().map(cargoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CargoDTO> findOne(Long id) {
        LOG.debug("Request to get Cargo : {}", id);
        return cargoRepository.findById(id).map(cargoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Cargo : {}", id);
        cargoRepository.deleteById(id);
    }
}

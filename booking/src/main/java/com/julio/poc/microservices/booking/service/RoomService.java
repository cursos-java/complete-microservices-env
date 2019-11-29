package com.julio.poc.microservices.booking.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.booking.dtos.RoomGetDTO;
import com.julio.poc.microservices.booking.dtos.RoomPostDTO;
import com.julio.poc.microservices.booking.entities.Room;
import com.julio.poc.microservices.booking.mappers.RoomMapper;
import com.julio.poc.microservices.booking.repositories.RoomRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    public RoomService(RoomRepository repository, RoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @TrackMethod
    @Transactional
    public RoomGetDTO save(@NonNull final RoomPostDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @TrackMethod
    @Transactional(readOnly = true)
    public Page<RoomGetDTO> search(@NonNull final Pageable pageable, Specification<Room> specification) {
        return mapper.toDTO(repository.findAll(specification, pageable));
    }

    @TrackMethod
    @Transactional(readOnly = true)
    public RoomGetDTO findById(@NonNull UUID id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }
}
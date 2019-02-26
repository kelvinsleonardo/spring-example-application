package br.com.kelvinsantiago.example.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/*
 * Class with utilities to manipulate DAO with modelmapper
 * Author: Kélvin Santiago
 * Date: 24/02/2019
 */
@Service
@RequiredArgsConstructor
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public <T> List<T> toList(Class<T> clazz, List<?> items) {
        return items.stream()
                .map(item -> modelMapper.map(item, clazz))
                .collect(Collectors.toList());
    }

    public <T> T toObject(Class<T> clazz, Object item) {
        if (item == null) return null;
        return modelMapper.map(item, clazz);
    }

}
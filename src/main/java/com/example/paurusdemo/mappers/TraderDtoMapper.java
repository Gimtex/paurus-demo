package com.example.paurusdemo.mappers;

import com.example.paurusdemo.repository.dao.Trader;
import com.example.paurusdemo.service.dto.TraderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraderDtoMapper {

    Trader convertFrom(TraderDto traderDto);

    TraderDto convertTo(Trader trader);
}

package com.example.paurusdemo.service.trader;

import com.example.paurusdemo.mappers.TraderDtoMapper;
import com.example.paurusdemo.repository.TraderRepository;
import com.example.paurusdemo.repository.dao.Trader;
import com.example.paurusdemo.service.dto.TraderDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraderService {

    private final TraderRepository traderRepository;
    private final TraderDtoMapper traderDtoMapper;

    public TraderService(TraderRepository traderRepository, TraderDtoMapper traderDtoMapper) {
        this.traderRepository = traderRepository;
        this.traderDtoMapper = traderDtoMapper;
    }

    public TraderDto findById(final Long id) {
        return traderRepository.findById(id)
                .map(traderDtoMapper::convertTo)
                .orElse(null);

    }

    public TraderDto store(final TraderDto traderDto) {
        return traderDtoMapper.convertTo(
                traderRepository.save(
                        traderDtoMapper.convertFrom(traderDto)
                )
        );
    }

    public TraderDto delete(final Long traderId) {
        Optional<Trader> optionalTrader = traderRepository.findById(traderId);

        if (optionalTrader.isPresent()) {
            Trader trader = optionalTrader.get();
            traderRepository.delete(trader);
            return traderDtoMapper.convertTo(trader);
        } else {
            return null;
        }
    }
}

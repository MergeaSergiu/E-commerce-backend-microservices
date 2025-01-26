package com.mycompany.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudCheckServiceImpl implements FraudCheckService{

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public FraudCheckServiceImpl(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
    }

    @Override
    public boolean isFraudulentCustomer(Integer customerId) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                        .customerId(customerId)
                                .isFraudster(false)
                                        .createdAt(LocalDateTime.now())
                                                .build();
        fraudCheckHistoryRepository.save(fraudCheckHistory);
        return false;
    }
}

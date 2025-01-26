package com.mycompany.fraud;

import org.springframework.http.HttpStatusCode;

public record FraudCheckResponse(Boolean isFraudster) { }

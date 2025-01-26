package com.mycompany.fraud;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {

    private final FraudCheckService fraudCheckService;
    public FraudController(FraudCheckService fraudCheckService){
        this.fraudCheckService = fraudCheckService;
    }

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
          boolean isFraudulentCustomer =  fraudCheckService.isFraudulentCustomer(customerId);
          return new FraudCheckResponse(isFraudulentCustomer);
    }

}

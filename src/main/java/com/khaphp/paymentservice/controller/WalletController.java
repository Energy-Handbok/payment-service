package com.khaphp.paymentservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOcreate;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOupdate;
import com.khaphp.paymentservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
//@SecurityRequirement(name = "EnergyHandbook")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/detail")
    public ResponseEntity<?> detailObject(String customerId){
        ResponseObject<Object> responseObject = walletService.getDerail(customerId);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> createObject(@RequestBody WalletDTOcreate walletDTOcreate){
        ResponseObject<Object> responseObject = walletService.create(walletDTOcreate);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/customer-balance")
    public ResponseEntity<?> updateObjectBalance(@RequestBody @Valid WalletDTOupdate object){
        ResponseObject<Object> responseObject = walletService.updateBalance(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject<Object> responseObject = walletService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}

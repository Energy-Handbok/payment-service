package com.khaphp.paymentservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.paymentservice.dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.paymentservice.service.WalletTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet-transaction")
//@SecurityRequirement(name = "EnergyHandbook")
@RequiredArgsConstructor
public class WalletTransactionController {
    private final WalletTransactionService walletTransactionService;

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject<Object> responseObject = walletTransactionService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<Object> getObject(String id){
        ResponseObject<Object> responseObject = walletTransactionService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<Object> createObject(@RequestBody @Valid WalletTransactionDTOcreate object){
        ResponseObject<Object> responseObject = walletTransactionService.create(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteObject(String id) {
        try{
            ResponseObject<Object> responseObject = walletTransactionService.delete(id);
            if(responseObject.getCode() == 200){
                return ResponseEntity.ok(responseObject);
            }
            return ResponseEntity.badRequest().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

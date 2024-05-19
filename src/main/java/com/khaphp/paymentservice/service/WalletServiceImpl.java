package com.khaphp.paymentservice.service;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.common.entity.UserSystem;
import com.khaphp.paymentservice.call.UserServiceCall;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOcreate;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOupdate;
import com.khaphp.paymentservice.entity.Wallet;
import com.khaphp.paymentservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserServiceCall userServiceCall;
    @Override
    public ResponseObject<Object> getDerail(String customerId) {
        try {
            UserSystem customer = userServiceCall.getObject(customerId);
            if(customer == null){
                throw new Exception("customer not found");
            }
            Wallet wallet = walletRepository.findByCustomerId(customerId).orElseThrow(() -> new Exception("wallet not found"));
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .data(wallet)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(WalletDTOcreate object) {
        try {
            UserSystem customer = userServiceCall.getObject(object.getCustomerId());
            if(customer == null){
                throw new Exception("customer not found");
            }
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            wallet.setCustomerId(object.getCustomerId());
            walletRepository.save(wallet);
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .data(wallet)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateBalance(WalletDTOupdate object) {
        try {
            Wallet wallet = walletRepository.findByCustomerId(object.getCustomerId())
                    .orElseThrow(() -> new Exception("wallet not found for user id: " + object.getCustomerId()));
            wallet.setBalance(wallet.getBalance() + object.getBalance());
            walletRepository.save(wallet);
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try {
            if(!walletRepository.existsById(id)){
                throw new Exception("object not found");
            }
            walletRepository.deleteById(id);
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }
}

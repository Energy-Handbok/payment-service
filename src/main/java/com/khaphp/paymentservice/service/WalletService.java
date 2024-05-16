package com.khaphp.paymentservice.service;


import com.khaphp.common.dto.ResponseObject;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOcreate;
import com.khaphp.paymentservice.dto.Wallet.WalletDTOupdate;

public interface WalletService {
    ResponseObject<Object> getDerail(String customerId);
    ResponseObject<Object> create(WalletDTOcreate object);
    ResponseObject<Object> updateBalance(WalletDTOupdate object);
    ResponseObject<Object> delete(String customerId);
}

package com.khaphp.paymentservice.service;


import com.khaphp.common.dto.ResponseObject;
import com.khaphp.paymentservice.dto.WalletTransaction.WalletTransactionDTOcreate;

public interface WalletTransactionService {
    ResponseObject<Object> getAll(int pageSize, int pageIndex);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(WalletTransactionDTOcreate object);
}

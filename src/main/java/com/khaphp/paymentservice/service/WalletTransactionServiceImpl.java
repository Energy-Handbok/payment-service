package com.khaphp.paymentservice.service;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.paymentservice.dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.paymentservice.entity.Wallet;
import com.khaphp.paymentservice.entity.WalletTransaction;
import com.khaphp.paymentservice.repository.WalletRepository;
import com.khaphp.paymentservice.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    public static final String SUCCESS_MSG = "Success";
    public static final String EXCEPTION_DEFAULT_MSG = "Exception: ";
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<WalletTransaction> objListPage = null;
        List<WalletTransaction> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = walletTransactionRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = walletTransactionRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(item -> {
            if(item.getDescription().contains("Nap tien vao vi Energy Handbook")){
                item.setDescription("Nạp tiền vào ví Energy Handbook");
            }
        });
        return ResponseObject.builder()
                .code(200).message(SUCCESS_MSG)
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            WalletTransaction object = walletTransactionRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(object)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_DEFAULT_MSG + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(WalletTransactionDTOcreate object) {
        try{
            Wallet wallet = walletRepository.findByCustomerId(object.getCustomerId())
                    .orElseThrow(() -> new Exception("wallet not found for customer: " + object.getCustomerId()));
            WalletTransaction walletTransaction = modelMapper.map(object, WalletTransaction.class);
//            walletTransaction.setCreateDate(new Date(System.currentTimeMillis()));
            walletTransaction.setWallet(wallet);
            walletTransactionRepository.save(walletTransaction);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MSG)
                    .data(walletTransaction)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_DEFAULT_MSG + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(String id) throws Exception {
        try{
            if(walletTransactionRepository.existsById(id)){
                walletTransactionRepository.deleteById(id);
                return ResponseObject.builder()
                        .code(200)
                        .message(SUCCESS_MSG)
                        .build();
            }else{
                System.out.println("transaction with id "+ id +" not found");
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
        return null;
    }
}

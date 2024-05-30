package com.khaphp.paymentservice.service.kafka;

import com.khaphp.common.dto.payment.WalletDTOupdate;
import com.khaphp.paymentservice.service.WalletService;
import com.khaphp.paymentservice.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
    private final WalletService walletService;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    @KafkaListener(topics = "UPDATE_WALLET_BALANCE", groupId = "energy-handbook-group", containerFactory = "objectListener")   //vì lắng nghe object gửi về nên phải có containerFactory, objectListener là method mà ta config để lắng nghe ấy (bên class config có ghi á)
    public void updateWalletBalance(WalletDTOupdate walletDTOupdate){
        log.info("rollback [updateWalletBalance] consume the message json : {}", walletDTOupdate.toString());
        try{
            com.khaphp.paymentservice.dto.Wallet.WalletDTOupdate dtoUpdate = modelMapper.map(walletDTOupdate, com.khaphp.paymentservice.dto.Wallet.WalletDTOupdate.class);
            walletService.updateBalance(dtoUpdate);
            log.info("rollback {} successfully", walletDTOupdate.toString());
        }catch (Exception e){
            log.error("rollback {} failed with error {}", walletDTOupdate.toString(), e.getMessage());
        }
    }

    @KafkaListener(topics = "DELETE_TRANSACTION", groupId = "energy-handbook-group")
    public void deleteTransactionById(String idTransaction){    //nớ lưu ý, khi nó nhận msg type String thì nó thường đi theo cặp "", nhớ lưu ý điê này, vd: "be1631de-e998-4c68-b189-ab483f392f7e" -> phải bỏ cặp "" thì mới đúng là id
        log.info("rollback [deleteTransactionById] consume the message : {}", idTransaction);
        try{
            walletTransactionService.delete(idTransaction.substring(1, idTransaction.length() - 1));
            log.info("rollback transaction {} successfully", idTransaction);
        }catch (Exception e){
            log.error("rollback transaction {} failed with error {}", idTransaction, e.getMessage());
        }
    }
}

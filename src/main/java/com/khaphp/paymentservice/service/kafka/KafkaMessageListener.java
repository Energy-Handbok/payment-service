package com.khaphp.paymentservice.service.kafka;

import com.khaphp.common.dto.payment.WalletDTOupdate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "UPDATE_WALLET_BALANCE", groupId = "energy-handbook-group", containerFactory = "objectListener")   //vì lắng nghe object gửi về nên phải có containerFactory, objectListener là method mà ta config để lắng nghe ấy (bên class config có ghi á)
    public void updateWalletBalance(com.khaphp.common.dto.payment.WalletDTOupdate walletDTOupdate){
        log.info("updateWalletBalance consume the message json : {}", walletDTOupdate.toString());
    }

    @KafkaListener(topics = "DELETE_TRANSACTION", groupId = "energy-handbook-group")
    public void deleteTransactionById(String idTransaction){
        log.info("deleteTransactionById consume the message : {}", idTransaction);
    }

    @KafkaListener(topics = "UPDATE_STOCK_FOOD", groupId = "energy-handbook-group", containerFactory = "objectListener")
    public void updateStockFood(WalletDTOupdate object) {
        log.info("updateStockFood consume the message json : {}", object.toString());
    }
}

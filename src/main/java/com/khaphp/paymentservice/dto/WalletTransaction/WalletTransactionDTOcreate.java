package com.khaphp.paymentservice.dto.WalletTransaction;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletTransactionDTOcreate {
    private String customerId;
    private String description;
    private int amount;
    private Date createDate;
}

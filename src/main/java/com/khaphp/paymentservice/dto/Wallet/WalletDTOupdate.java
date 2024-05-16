package com.khaphp.paymentservice.dto.Wallet;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletDTOupdate {
    private String customerId;
    private int balance;
}

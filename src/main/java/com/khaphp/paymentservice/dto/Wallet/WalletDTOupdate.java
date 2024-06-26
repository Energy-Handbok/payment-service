package com.khaphp.paymentservice.dto.Wallet;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WalletDTOupdate implements Serializable {
    private String customerId;
    private int balance;
}

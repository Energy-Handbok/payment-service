package com.khaphp.paymentservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private int balance;
    @Column(columnDefinition = "VARCHAR(36)")
    private String customerId;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WalletTransaction> walletTransactions;
}

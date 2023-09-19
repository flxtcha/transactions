package com.example.transactions.model;

import io.cucumber.java.mk_latn.No;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id @Getter @Setter
    private String id;
    @Getter @Setter
    private String accountName;
    @Getter @Setter
    private BigDecimal accountBalance;

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }

}

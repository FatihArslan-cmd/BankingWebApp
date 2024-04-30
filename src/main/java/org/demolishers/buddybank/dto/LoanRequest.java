package org.demolishers.buddybank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
   private  String loanType;
    private Long toAccountId;
    private Long amount;
    private String description;
}

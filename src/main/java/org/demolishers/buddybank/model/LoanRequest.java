package org.demolishers.buddybank.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    private User user;
}

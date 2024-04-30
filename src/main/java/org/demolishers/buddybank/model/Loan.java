    package org.demolishers.buddybank.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder //hgf
    @Entity
    public class Loan {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private LocalDate expiryDate;

        @Column
        private String loanType;

        @Column
        private Long balance;

        @Column
        private String description;

        @Column
        private String response;
        @Column
        private byte isItaboveLimit;
        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonIgnore
        private Account account;

    }

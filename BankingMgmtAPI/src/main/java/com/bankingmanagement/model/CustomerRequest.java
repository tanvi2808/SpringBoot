package com.bankingmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private int custId;

    @NotNull
    private String customerName;

    @NotNull
    private String customerAddress;

    @NotNull
    private Long customerPhone;

}

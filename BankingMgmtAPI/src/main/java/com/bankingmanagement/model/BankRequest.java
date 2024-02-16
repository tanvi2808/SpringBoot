package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankRequest {

    private int bankCode;

    @NotNull
    private String bankName;
    @NotNull
    @Length(min=3, max = 50)
    private String bankAddress;
}

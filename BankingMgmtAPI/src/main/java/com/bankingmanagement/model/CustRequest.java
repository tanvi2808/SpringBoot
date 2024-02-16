package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CustRequest {

    @JsonIgnore
    private int custId;

    @NotNull
    @Length(min=3, max=30)
    private String custName;

    @Pattern(regexp = "\\d{6,12}")
    private long custPhone;

    @Min(3)
    private String custAddress;
}

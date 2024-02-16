package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class BranchRequest {

    @JsonIgnore
    private int branchId;

    @NotNull
    private String branchName;
    @NotNull
    @Length(min = 3, max = 50)
    private String branchAddress;
    @NotNull
    private int bank_code;
}

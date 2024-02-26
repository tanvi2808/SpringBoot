package com.spring.reporting.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
    @Setter
    public class BankDTO {
        private String bankName;
        private String bankAddress;
        private List<BranchDTO> branches;

}

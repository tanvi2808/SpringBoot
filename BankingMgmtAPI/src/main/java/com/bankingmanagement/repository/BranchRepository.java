package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    public Optional<Branch> findByBranchName(String branchName);
    public void deleteByBranchName(String branchName);
}

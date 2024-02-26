package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Branch;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BranchRepositoryTest {

    @Autowired
    BranchRepository branchRepository;
    @BeforeEach
    public void setUp(){
        Branch branch = new Branch();
        branch.setBranchName("BR1");
        branch.setBranchAddress("Delhi");
        branch.setBranchId(222);
        branchRepository.save(branch);
    }

    @Test
    public void findAllBranches_whenBranchFound_thenReturnAllBranch(){

        List<Branch> branches = branchRepository.findAll();
        Assert.assertEquals(1,branches.size());

    }

    @Test
    public void findByBranchName_whenValidBranchName_thenReturnBranch(){

        Optional<Branch> branch = branchRepository.findByBranchName("BR1");
        Assert.assertTrue(branch.isPresent());
    }
    @Test
    public void addNewBranch_whenValidBData_thenReturnSavedBranch(){

        Optional<Branch> branch = branchRepository.findByBranchName("BR1");
        Assert.assertTrue(branch.isPresent());
    }

    @Test
    public void deleteBranchById_whenBranchPresent_thenBranchNotReturned(){

        List<Branch> branches = branchRepository.findAll();
        Assertions.assertEquals(1,branches.size());
        branchRepository.deleteById(branches.get(0).getBranchId());
        List<Branch> branches2 = branchRepository.findAll();
        Assertions.assertEquals(0,branches2.size());

    }

    @Test
    public void deleteBranchByName_whenBranchPresent_thenBranchNotReturned(){

        List<Branch> branches = branchRepository.findAll();
        Assertions.assertEquals(1,branches.size());
        branchRepository.deleteByBranchName("BR1");
        List<Branch> branches2 = branchRepository.findAll();
        Assertions.assertEquals(0,branches2.size());

    }

}

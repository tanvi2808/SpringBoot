package org.springproject.data.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Indexed;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name="IDX_EMAIL", columnList = "email")})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String username;

    @Column
    String email;

    @Column
    String password;

    @CreatedDate
    LocalDateTime creationAt;

    @LastModifiedDate
    LocalDateTime modifiedAt;
}

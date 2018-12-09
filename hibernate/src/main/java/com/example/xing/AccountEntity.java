package com.example.xing;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 2:56 PM.
 */
@Entity
@Table(name = "Account")
public class AccountEntity implements Serializable {
    private static final long    serialVersionUID = 1L;
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private              Integer accountId;
    @Column(name = "ACC_NO", unique = false, nullable = false, length = 100)
    private              String  accountNumber;

    @OneToOne(mappedBy = "accounts", fetch = FetchType.LAZY)
    private EmployeeEntity employee;

}
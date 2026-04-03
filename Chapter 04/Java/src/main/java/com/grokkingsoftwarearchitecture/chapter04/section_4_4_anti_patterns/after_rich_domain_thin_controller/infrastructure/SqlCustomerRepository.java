package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models.Customer;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.CustomerRepository;

import org.springframework.stereotype.Repository;
/**
 * ARCHITECTURE NOTE: By isolating SQL logic here, we prevent 
 * database concerns from "leaking" into the Presentation or 
 * Business layers.
 */
@Repository
public class SqlCustomerRepository implements CustomerRepository {
    @Override
    public Customer getById(int customerId) {
        Customer c = new Customer();
        c.setId(customerId);
        c.setType("Gold");
        c.setEmail("a@b.com");
        return c;
    }
}
package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.data_access;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain_models.Customer;

public class SqlCustomerRepository implements CustomerRepository {
    @Override
    public Customer getById(int customerId) {
        Customer c = new Customer();
        c.id = customerId;
        c.type = "Gold";
        c.email = "a@b.com";
        return c;
    }
}
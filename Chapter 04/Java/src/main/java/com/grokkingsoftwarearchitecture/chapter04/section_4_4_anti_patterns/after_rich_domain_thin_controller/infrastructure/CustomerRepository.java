package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.Customer;

public interface CustomerRepository {
    Customer getById(int customerId);
}
package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.businesslogic;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domainmodels.Item;
import java.util.List;

/**
 * DTO (Data Transfer Object) for incoming requests.
 */
public class OrderRequest {
    public int customerId;
    public List<Item> items;
}
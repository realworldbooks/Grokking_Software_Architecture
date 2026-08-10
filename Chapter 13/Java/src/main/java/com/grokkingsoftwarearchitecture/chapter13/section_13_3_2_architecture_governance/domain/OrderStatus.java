package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.domain;

/**
 * The lifecycle states of an Order.
 *
 * This enum lives in the Domain layer alongside the Order entity.
 * It represents pure business vocabulary with no infrastructure
 * dependencies whatsoever.
 */
public enum OrderStatus {
    /** Order has been created but not yet paid. */
    PENDING,

    /** Payment has been received. */
    PAID,

    /** Order has been dispatched to the customer. */
    SHIPPED,

    /** Order was cancelled before shipping. */
    CANCELLED
}
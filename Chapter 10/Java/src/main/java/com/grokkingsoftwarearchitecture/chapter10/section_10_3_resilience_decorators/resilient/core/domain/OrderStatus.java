package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.domain;

/**
 * * DESIGN NOTE:
 * Strictly enforcing system states via Enums prevents "Magic String" contamination. 
 * By defining this inside 'core/domain', we ensure the entire system speaks the 
 * same language, regardless of which external cloud provider we use.
 */
public enum OrderStatus {
    PENDING_PAYMENT,
    PAID,
    FAILED
}
package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.after;

/**
 * The Strictly Enforced Contract.
 * * ARCHITECTURE NOTE: We have redefined what it means to be a "Player" in this 
 * specific context. This base class now implicitly means "Field Player". 
 * Any class that inherits from this MUST be able to execute field assignments.
 * (If we still needed a Goalie, we would create a separate hierarchy or use 
 * interfaces to segregate those specific abilities, as seen in the ISP lesson!)
 */
public abstract class Player {
    public abstract void playFieldPosition();
}
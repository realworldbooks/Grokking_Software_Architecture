package com.grokkingsoftwarearchitecture.chapter03.section_3_3_2_ocp.after;

/**
 * OCP SOLUTION: Closed for Modification.
 * * ARCHITECTURE NOTE: This class is now perfectly "Closed for modification." 
 * Notice that there are absolutely no if/else statements here. If the coach 
 * invents 100 new plays, we will NEVER have to open, edit, or recompile this 
 * Midfielder class. The risk of breaking existing functionality is zero!
 */
public class Midfielder {
    
    /**
     * Executes any play dynamically through polymorphism.
     * * @param play Any class that implements the Play interface.
     */
    public void executePlay(Play play) {
        // The Midfielder doesn't need to know WHAT the play is, 
        // it just knows HOW to execute it!
        play.execute();
    }
}
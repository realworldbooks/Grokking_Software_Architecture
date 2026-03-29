package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.after;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: LSP (AFTER) ===");
        System.out.println("Subclasses perfectly fulfill the parent contract!\n");

        Coach coach = new Coach();
        Midfielder midfielder = new Midfielder();
        Forward forward = new Forward();

        coach.directFieldPlay(midfielder);
        System.out.println();
        coach.directFieldPlay(forward);

        System.out.println("\n===============================\n");
    }
}
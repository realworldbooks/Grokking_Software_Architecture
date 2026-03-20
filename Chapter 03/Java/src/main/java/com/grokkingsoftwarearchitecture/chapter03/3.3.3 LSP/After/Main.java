package com.grokkingsoftwarearchitecture.chapter03.lsp.after;

public class Main {
    public static void main(String[] args) {
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
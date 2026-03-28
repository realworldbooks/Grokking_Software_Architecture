package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: LSP (BEFORE) ===");
        System.out.println("Passing a Goalie as a generic Player breaks the contract!\n");

        Coach coach = new Coach();
        Goalie goalie = new Goalie();

        coach.directFieldPlay(goalie);

        System.out.println("\n===============================\n");
    }
}
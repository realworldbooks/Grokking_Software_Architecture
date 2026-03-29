package com.grokkingsoftwarearchitecture.chapter03.section_3_3_5_dip.after;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void run() {
        System.out.println("=== Chapter 3: DIP (AFTER) ===");
        System.out.println("The Coach depends on the Player abstraction, allowing for easy team changes!\n");

        List<Player> team = Arrays.asList(
            new Forward(),
            new Midfielder(),
            new Winger()
        );

        Coach coach = new Coach(team);
        coach.executeGamePlan();

        System.out.println("\n===============================\n");
    }
}
package gateways;

import java.io.*;
import java.util.*;

public class MealPlanStorage {

    private static String filename(String username) {
        return "mealplan_" + username + ".txt";
    }

    public static void saveMealPlan(String username,
                                    Map<String, List<String>> mealPlan) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename(username)))) {
            for (String day : mealPlan.keySet()) {
                writer.println("DAY:" + day);
                for (String meal : mealPlan.get(day)) {
                    writer.println("MEAL:" + meal);
                }
                writer.println("ENDDAY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<String>> loadMealPlan(String username) {

        File file = new File(filename(username));
        if (!file.exists()) {
            return null;
        }

        Map<String, List<String>> mealPlan = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentDay = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("DAY:")) {
                    currentDay = line.substring(4);
                    mealPlan.put(currentDay, new ArrayList<>());
                }
                else if (line.startsWith("MEAL:") && currentDay != null) {
                    mealPlan.get(currentDay).add(line.substring(5));
                }
                else if (line.equals("ENDDAY")) {
                    currentDay = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return mealPlan;
    }
}
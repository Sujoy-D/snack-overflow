package data_access;

import java.io.*;
import java.util.*;

public class UserTagFileDataAccess implements TaggingDataAccessInterface {
    private static final String FILE_PATH =  "src/main/java/data_access/temp_tags.txt";

    private Map<String, Map<Integer, List<String>>> loadAllTags() {
        Map<String, Map<Integer, List<String>>> result = new HashMap<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return result;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", 3);
                String username = parts[0];
                int recipeID;
                try {
                    recipeID = Integer.parseInt(parts[1]);
                }  catch (NumberFormatException e) {
                    continue;
                }
                String tagName = parts[2];
                Map<Integer, List<String>> userMap = result.computeIfAbsent(username, k -> new HashMap<>());
                List<String> tags = userMap.computeIfAbsent(recipeID, k -> new ArrayList<>());
                if (!tags.contains(tagName)) {
                    tags.add(tagName.toLowerCase().trim());
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private void saveAllTags(Map<String, Map<Integer, List<String>>> allTags) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Map<Integer, List<String>>> userEntry : allTags.entrySet()) {
                String username = userEntry.getKey();
                for (Map.Entry<Integer, List<String>> recipeEntry : userEntry.getValue().entrySet()) {
                    int recipeID = recipeEntry.getKey();
                    for (String tag :  recipeEntry.getValue()) {
                        bw.write(username + "|" + recipeID + "|" + tag);
                        bw.newLine();
                    }
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTagToRecipe(String username, int recipeID, String tagName) {
        Map<String, Map<Integer, List<String>>> allTags = loadAllTags();
        Map<Integer, List<String>> userMap = allTags.computeIfAbsent(username, k -> new HashMap<>());
        List<String> tags = userMap.computeIfAbsent(recipeID, k -> new ArrayList<>());
        if (!tags.contains(tagName)) {
            tags.add(tagName.toLowerCase().trim());
            saveAllTags(allTags);
        }

    }

    @Override
    public List<String> getTagsForRecipe(String username, int recipeID) {
        Map<String, Map<Integer, List<String>>> allTags = loadAllTags();
        Map<Integer, List<String>> userMap = allTags.get(username);
        if (userMap == null) {
            return new ArrayList<>();
        }
        List<String> tags = userMap.get(recipeID);
        return tags == null ? new ArrayList<>() : new ArrayList<>(tags);
    }
}

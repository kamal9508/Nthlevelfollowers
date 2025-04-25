import java.util.*;

public class NthLevelFollowers {
    static class User {
        int id;
        List<Integer> follows;

        User(int id, List<Integer> follows) {
            this.id = id;
            this.follows = follows;
        }
    }

    public static void main(String[] args) {
        // Example Input
        Map<String, Object> input = new HashMap<>();
        input.put("n", 2);
        input.put("findId", 1);

        List<Map<String, Object>> usersList = new ArrayList<>();
        usersList.add(createUser(1, Arrays.asList(2, 3)));
        usersList.add(createUser(2, Arrays.asList(4)));
        usersList.add(createUser(3, Arrays.asList(4, 5)));
        usersList.add(createUser(4, Arrays.asList(6)));
        usersList.add(createUser(5, Arrays.asList(6)));
        usersList.add(createUser(6, Arrays.asList()));

        input.put("users", usersList);

        // Call the function to find Nth-level followers
        List<Integer> result = findNthLevelFollowers(input);
        
        // Print the result in the expected format
        System.out.println("{ \"regNo\": \"REG12347\", \"outcome\": " + result + " }");
    }

    // Helper method to create a user
    private static Map<String, Object> createUser(int id, List<Integer> follows) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("follows", follows);
        return user;
    }

    // Method to find Nth-level followers
    public static List<Integer> findNthLevelFollowers(Map<String, Object> input) {
        int n = (int) input.get("n");
        int findId = (int) input.get("findId");

        // Parse users list into a map for quick access
        Map<Integer, List<Integer>> userFollowsMap = new HashMap<>();
        List<Map<String, Object>> users = (List<Map<String, Object>>) input.get("users");
        for (Map<String, Object> user : users) {
            int id = (int) user.get("id");
            List<Integer> follows = (List<Integer>) user.get("follows");
            userFollowsMap.put(id, follows);
        }

        // BFS to find nth level followers
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(findId);
        visited.add(findId);

        int level = 0;

        while (!queue.isEmpty() && level < n) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currentUser = queue.poll();
                List<Integer> followers = userFollowsMap.getOrDefault(currentUser, new ArrayList<>());
                for (int followId : followers) {
                    if (!visited.contains(followId)) {
                        queue.add(followId);
                        visited.add(followId);
                    }
                }
            }
            level++;
        }

        // At nth level, collect all the IDs
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }

        return result;
    }
}

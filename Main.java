import java.util.*;

public class Main {

    // =========================
    // Study Task Class
    // =========================
    static class StudyTask {
        String subject;
        String topic;
        String priority;
        String deadline;

        StudyTask(String subject, String topic,
                  String priority, String deadline) {
            this.subject = subject;
            this.topic = topic;
            this.priority = priority;
            this.deadline = deadline;
        }

        @Override
        public String toString() {
            return subject + " - " + topic +
                    " [" + priority + "] - " + deadline;
        }
    }

    // =========================
    // Tree Node
    // =========================
    static class TreeNode {
        String topic;
        TreeNode left;
        TreeNode right;

        TreeNode(String topic) {
            this.topic = topic;
        }
    }

    // =========================
    // Data Structures
    // =========================

    // 1. ArrayList
    static ArrayList<StudyTask> allTasks = new ArrayList<>();

    // 2. Queue
    static Queue<StudyTask> taskQueue = new LinkedList<>();

    // 3. Stack
    static Stack<StudyTask> completedStack = new Stack<>();

    // 4. Priority Queue
    static PriorityQueue<StudyTask> priorityQueue =
            new PriorityQueue<>(
                    Comparator.comparingInt(Main::getPriorityValue)
            );

    // 5. HashMap
    static HashMap<String, ArrayList<String>> subjectTopics =
            new HashMap<>();

    // 6. Tree
    static TreeNode root = null;

    // 7. Graph
    static HashMap<String, ArrayList<String>> graph =
            new HashMap<>();


    // =========================
    // Priority Value
    // =========================
    static int getPriorityValue(StudyTask task) {

        if (task.priority.equalsIgnoreCase("HIGH")) {
            return 1;
        }

        if (task.priority.equalsIgnoreCase("MEDIUM")) {
            return 2;
        }

        return 3;
    }


    // =========================
    // Add Task
    // =========================
    static void addTask(Scanner sc) {

        System.out.println("\n--- ADD STUDY TASK ---");

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();

        System.out.print("Enter Topic: ");
        String topic = sc.nextLine();

        System.out.print("Enter Priority (HIGH/MEDIUM/LOW): ");
        String priority = sc.nextLine().toUpperCase();

        System.out.print("Enter Deadline: ");
        String deadline = sc.nextLine();

        StudyTask task =
                new StudyTask(subject, topic, priority, deadline);

        // ArrayList
        allTasks.add(task);

        // Queue
        taskQueue.add(task);

        // Priority Queue
        priorityQueue.add(task);

        // HashMap
        subjectTopics.putIfAbsent(
                subject,
                new ArrayList<>()
        );

        subjectTopics.get(subject).add(topic);

        // Tree
        root = insertTree(root, topic);

        // Graph
        graph.putIfAbsent(subject, new ArrayList<>());
        graph.putIfAbsent(topic, new ArrayList<>());

        graph.get(subject).add(topic);
        graph.get(topic).add(subject);

        System.out.println("\nTask Added Successfully!");
    }


    // =========================
    // View All Tasks
    // =========================
    static void viewAllTasks() {

        System.out.println("\n--- ALL STUDY TASKS ---");

        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        int count = 1;

        for (StudyTask task : taskQueue) {

            System.out.println(
                    count + ". " +
                    task.subject + " - " +
                    task.topic +
                    " | Priority: " +
                    task.priority +
                    " | Deadline: " +
                    task.deadline
            );

            count++;
        }
    }


    // =========================
    // View Priority Task
    // =========================
    static void viewPriorityTask() {

        System.out.println("\n--- NEXT PRIORITY TASK ---");

        if (priorityQueue.isEmpty()) {
            System.out.println("No pending tasks.");
            return;
        }

        StudyTask task = priorityQueue.peek();

        System.out.println("Subject  : " + task.subject);
        System.out.println("Topic    : " + task.topic);
        System.out.println("Priority : " + task.priority);
        System.out.println("Deadline : " + task.deadline);
    }


    // =========================
    // Complete Priority Task
    // =========================
    static void completeTask() {

        System.out.println("\n--- COMPLETE TASK ---");

        if (priorityQueue.isEmpty()) {
            System.out.println("No tasks to complete.");
            return;
        }

        // Highest priority task
        StudyTask task = priorityQueue.poll();

        // Remove from Queue
        taskQueue.remove(task);

        // Add to Stack
        completedStack.push(task);

        System.out.println("\nTask Completed!");

        System.out.println(
                "Completed: " +
                task.subject +
                " - " +
                task.topic
        );
    }


    // =========================
    // Undo Completed Task
    // =========================
    static void undoCompletedTask() {

        System.out.println("\n--- UNDO COMPLETED TASK ---");

        if (completedStack.isEmpty()) {
            System.out.println("No completed task to undo.");
            return;
        }

        StudyTask task = completedStack.pop();

        // Add task back
        taskQueue.add(task);
        priorityQueue.add(task);

        System.out.println("\nUndo Successful!");

        System.out.println(
                "Task moved back to pending: " +
                task.subject + " - " +
                task.topic
        );
    }


    // =========================
    // Search Topic using HashMap
    // =========================
    static void searchTopic(Scanner sc) {

        System.out.println("\n--- SEARCH SUBJECT / TOPIC ---");

        System.out.print("Enter Subject: ");

        String subject = sc.nextLine();

        if (!subjectTopics.containsKey(subject)) {

            System.out.println(
                    "Subject not found."
            );

            return;
        }

        System.out.println("\nSubject: " + subject);

        ArrayList<String> topics =
                subjectTopics.get(subject);

        System.out.println("Topics:");

        for (String topic : topics) {
            System.out.println("- " + topic);
        }
    }


    // =========================
    // Tree Insert
    // =========================
    static TreeNode insertTree(
            TreeNode node,
            String topic) {

        if (node == null) {
            return new TreeNode(topic);
        }

        if (topic.compareToIgnoreCase(node.topic) < 0) {

            node.left =
                    insertTree(node.left, topic);

        } else if (
                topic.compareToIgnoreCase(node.topic) > 0) {

            node.right =
                    insertTree(node.right, topic);
        }

        return node;
    }


    // =========================
    // Tree Inorder
    // =========================
    static void inorder(TreeNode node) {

        if (node == null) {
            return;
        }

        inorder(node.left);

        System.out.println("- " + node.topic);

        inorder(node.right);
    }


    // =========================
    // Display Tree
    // =========================
    static void displayTree() {

        System.out.println("\n--- TOPIC TREE ---");

        if (root == null) {
            System.out.println("No topics available.");
            return;
        }

        System.out.println("Topics in sorted order:");

        inorder(root);
    }


    // =========================
    // Graph BFS
    // =========================
    static void graphBFS(Scanner sc) {

        System.out.println("\n--- GRAPH BFS ---");

        if (graph.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }

        System.out.print(
                "Enter starting Subject/Topic: "
        );

        String start = sc.nextLine();

        if (!graph.containsKey(start)) {

            System.out.println(
                    "Node not found in graph."
            );

            return;
        }

        Queue<String> bfsQueue =
                new LinkedList<>();

        HashSet<String> visited =
                new HashSet<>();

        bfsQueue.add(start);
        visited.add(start);

        System.out.println("\nBFS Traversal:");

        while (!bfsQueue.isEmpty()) {

            String current =
                    bfsQueue.poll();

            System.out.print(
                    current + " -> "
            );

            for (String neighbour :
                    graph.get(current)) {

                if (!visited.contains(neighbour)) {

                    visited.add(neighbour);

                    bfsQueue.add(neighbour);
                }
            }
        }

        System.out.println("END");
    }


    // =========================
    // View Progress
    // =========================
    static void viewProgress() {

        System.out.println("\n--- STUDY PROGRESS ---");

        int total = allTasks.size();

        int completed =
                completedStack.size();

        int pending =
                taskQueue.size();

        System.out.println(
                "Total Tasks     : " + total
        );

        System.out.println(
                "Completed Tasks : " + completed
        );

        System.out.println(
                "Pending Tasks   : " + pending
        );

        if (total > 0) {

            double percentage =
                    ((double) completed / total) * 100;

            System.out.printf(
                    "Progress        : %.2f%%\n",
                    percentage
            );

        } else {

            System.out.println(
                    "Progress        : 0%"
            );
        }
    }


    // =========================
    // Main Menu
    // =========================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println(
                    "\n===================================="
            );

            System.out.println(
                    "       SMART STUDY PLANNER"
            );

            System.out.println(
                    "===================================="
            );

            System.out.println(
                    "1. Add Study Task"
            );

            System.out.println(
                    "2. View All Tasks"
            );

            System.out.println(
                    "3. View Next Priority Task"
            );

            System.out.println(
                    "4. Complete Priority Task"
            );

            System.out.println(
                    "5. Undo Completed Task"
            );

            System.out.println(
                    "6. Search Subject / Topic"
            );

            System.out.println(
                    "7. View Topic Tree"
            );

            System.out.println(
                    "8. Graph BFS"
            );

            System.out.println(
                    "9. View Study Progress"
            );

            System.out.println(
                    "10. Exit"
            );

            System.out.println(
                    "===================================="
            );

            System.out.print(
                    "Enter your choice: "
            );

            choice = Integer.parseInt(
                    sc.nextLine()
            );


            switch (choice) {

                case 1:
                    addTask(sc);
                    break;

                case 2:
                    viewAllTasks();
                    break;

                case 3:
                    viewPriorityTask();
                    break;

                case 4:
                    completeTask();
                    break;

                case 5:
                    undoCompletedTask();
                    break;

                case 6:
                    searchTopic(sc);
                    break;

                case 7:
                    displayTree();
                    break;

                case 8:
                    graphBFS(sc);
                    break;

                case 9:
                    viewProgress();
                    break;

                case 10:
                    System.out.println(
                            "\nThank you for using " +
                            "Smart Study Planner!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Try again."
                    );
            }

        } while (choice != 10);

        sc.close();
    }
}
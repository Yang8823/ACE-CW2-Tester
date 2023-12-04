package org.example.Solution;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Walk {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Create Scanner object
//        Scanner input = new Scanner(new File("C:\\Users\\tanye\\Downloads\\ACE_Coursework2\\Walk\\src\\walkTestCase3.txt")); // Create Scanner object
//        Scanner input = new Scanner(new FileInputStream("testInput.txt")); // Create Scanner object

        int numOfColumns;    // `n` indicating the number of columns of the grid
        int numOfRows;      // `m` indicating the number of rows of the gird

        numOfColumns = input.nextInt(); // scan for `n`
        numOfRows = input.nextInt();    // scan for `m`

        // For debugging purpose
//        System.out.println("Number of Columns: " + numOfColumns);
//        System.out.println("Number of Rows: " + numOfRows);

        input.nextLine();

        String inputStartFinish = input.nextLine();
        List<Integer> start_finish_coordinates = getStartFinish(inputStartFinish);

        int startColumn = start_finish_coordinates.get(0);
        int startRow = start_finish_coordinates.get(1);
        int finishColumn = start_finish_coordinates.get(2);
        int finishRow = start_finish_coordinates.get(3);

        // For debugging purpose
//        System.out.println("Start Cell: " + startRow + "," + startColumn);
//        System.out.println("Finish Cell: " + finishRow + "," + finishColumn);

        int jumpBlock;  // k
        jumpBlock = input.nextInt();

        input.nextLine();

//        System.out.println("k: " + jumpBlock);

        Graph graph = new Graph(numOfRows, numOfColumns);
//        Graph graph = new Graph();

//        Node startNode = new Node(startRow, startColumn, 0);    // start cell (node)
//        Node finishNode = new Node(finishRow, finishColumn, 0); // destination cell (node)

//        int[][] matrix = new int[numOfRows][numOfColumns];
//
//        for(int i = 0; i < numOfRows; i++){
//            for(int j = 0; j < numOfColumns; j++){
//                matrix[i][j] = input.nextInt();
//            }
//        }
//
//        for(int[] row : matrix){
//            for(int element : row){
//                System.out.print(element + " ");
//            }
//            System.out.println();
//        }

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                int value = input.nextInt();
                boolean blocked;
                if (value == 1){
                    blocked = true;
                } else {
                    blocked = false;
                }
                Node currentNode = new Node(i, j, value, blocked);

                graph.addNode(currentNode);
            }
        }

//        graph.printGraph();

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                Node currentNode = graph.getNode(i, j);

                if (jumpBlock == 0) {
                    // Connect to adjacent cells (up, down, left, right)
                    // Connect to adjacent cells (up, down, left, right)
                    if (i > 0) {
                        //                    Node up = new Node(i - 1, j, value);
                        //                    graph.addNode(up);

                        Node up = graph.getNode(i - 1, j);
                        if (up.blocked == false){
                            graph.addEdge(currentNode, up);
                        }
                    }
                    if (i < numOfRows - 1) {
                        //                    Node down = new Node(i + 1, j, value);
                        //                    graph.addNode(down);

                        Node down = graph.getNode(i + 1, j); // Get the cell below
                        if (down.blocked == false){
                            graph.addEdge(currentNode, down); // Connect current cell to the cell below
                        }
                    }
                    if (j > 0) {
                        //                    Node left = new Node(i, j - 1, value);
                        //                    graph.addNode(left);

                        Node left = graph.getNode(i, j - 1); // Get the cell on the left
                        if (left.blocked == false){
                            graph.addEdge(currentNode, left);
                        }
                    }
                    if (j < numOfColumns - 1) {
                        //                    Node right = new Node(i, j + 1, value);
                        //                    graph.addNode(right);

                        Node right = graph.getNode(i, j + 1); // Get the cell on the right
                        if (right.blocked == false){
                            graph.addEdge(currentNode, right);
                        }
                    }

                    // Diagonal connections
                    if (i > 0 && j > 0) {
                        Node upLeft = graph.getNode(i - 1, j - 1);
                        if (upLeft.blocked == false){
                            graph.addEdge(currentNode, upLeft);
                        }
                    }
                    if (i > 0 && j < numOfColumns - 1) {
                        Node upRight = graph.getNode(i - 1, j + 1);
                        if (upRight.blocked == false) {
                            graph.addEdge(currentNode, upRight);
                        }
                    }
                    if (i < numOfRows - 1 && j > 0) {
                        Node downLeft = graph.getNode(i + 1, j - 1);
                        if (downLeft.blocked == false){
                            graph.addEdge(currentNode, downLeft);
                        }
                    }
                    if (i < numOfRows - 1 && j < numOfColumns - 1) {
                        Node downRight = graph.getNode(i + 1, j + 1);
                        if (downRight.blocked == false) {
                            graph.addEdge(currentNode, downRight);
                        }
                    }

                } else if (jumpBlock > 0) {
                    int jump = 1;

                    // done
                    int jumpBlockRight = jumpBlock;
                    while (jumpBlockRight != -1 && j + jump < numOfColumns) {
                        Node right = graph.getNode(i, j + jump);
                        if (right.blocked == true) {
                            jumpBlockRight--;
                        } else {
                            graph.addEdge(currentNode, right);
                        }
                        jump++;
                    }

                    // done
                    jump = 1;
                    int jumpBlockLeft = jumpBlock;
                    while (jumpBlockLeft != -1 && j - jump >= 0) {
                        Node left = graph.getNode(i, j - jump);
                        if (left.blocked == true) {
                            jumpBlockLeft--;
                        } else {
                            graph.addEdge(currentNode, left);
                        }
                        jump++;
                    }

                    // done
                    jump = 1;
                    int jumpBlockUp = jumpBlock;
                    while (jumpBlockUp != -1 && i - jump >= 0) {
                        Node up = graph.getNode(i - jump, j);
                        if (up.blocked == true) {
                            jumpBlockUp--;
                        } else {
                            graph.addEdge(currentNode, up);
                        }
                        jump++;
                    }

                    // done
                    jump = 1;
                    int jumpBlockDown = jumpBlock;
                    while (jumpBlockDown != -1 && i + jump < numOfRows) {
                        Node down = graph.getNode(i + jump, j);
                        if (down.blocked == true) {
                            jumpBlockDown--;
                        } else {
                            graph.addEdge(currentNode, down);
                        }
                        jump++;
                    }

                    jump = 1;
                    int jumpBlockUpLeft = jumpBlock;
                    while (jumpBlockUpLeft != -1 && i - jump >= 0 && j - jump >= 0) {
                        Node upLeft = graph.getNode(i - jump, j - jump);
                        if (upLeft.blocked == true) {
                            jumpBlockUpLeft--;
                        } else {
                            graph.addEdge(currentNode, upLeft);
                        }
                        jump++;
                    }

                    jump = 1;
                    int jumpBlockUpRight = jumpBlock;
                    while (jumpBlockUpRight != -1 && i - jump >= 0 && j + jump < numOfColumns) {
                        Node upRight = graph.getNode(i - jump, j + jump);
                        if (upRight.blocked == true) {
                            jumpBlockUpRight--;
                        } else {
                            graph.addEdge(currentNode, upRight);
                        }
                        jump++;
                    }

                    jump = 1;
                    int jumpBlockDownLeft = jumpBlock;
                    while (jumpBlockDownLeft != -1 && i + jump < numOfRows && j - jump >= 0) {
                        Node downLeft = graph.getNode(i + jump, j - jump);
                        if (downLeft.blocked == true) {
                            jumpBlockDownLeft--;
                        } else {
                            graph.addEdge(currentNode, downLeft);
                        }
                        jump++;
                    }

                    jump = 1;
                    int jumpBlockDownRight = jumpBlock;
                    while (jumpBlockDownRight != -1 && i + jump < numOfRows && j + jump < numOfColumns) {
                        Node downRight = graph.getNode(i + jump, j + jump);
                        if (downRight.blocked == true) {
                            jumpBlockDownRight--;
                        } else {
                            graph.addEdge(currentNode, downRight);
                        }
                        jump++;
                    }


                }

//                int jump = 1;
//                while (jumpBlock != 0 && j - 1 - jump >= 0){
//                    // Horizontal connections
//                    Node left = graph.getNode(i, j - 1 - jump);
//                    if (left.blocked == true){
//                        jumpBlock--;
//                    }
//                    graph.addEdge(currentNode, left);
//                    jump++;
//                    System.out.println("Jump: " + jump);
//                }

//                // when k, jump condition is > 0
//                // Horizontal and Vertical connections considering 'k' for jumping
//                for (int jump = 1; jump <= jumpBlock; jump++) {
//                    // Horizontal connections
//                    if (j > jump) {
//                        Node left = graph.getNode(i, j - jump);
////                        Node left = graph.getNode(i, j - 1 - jump);
//
//                        graph.addEdge(currentNode, left);
//                    }
//                    if (j < numOfColumns - jump) {
//                        Node right = graph.getNode(i, j + jump);
////                        Node right = graph.getNode(i, j + 1 + jump);
//
//                        graph.addEdge(currentNode, right);
//                    }
//
//                    // Vertical connections
//                    if (i > jump) {
//                        Node up = graph.getNode(i - jump, j);
//                        graph.addEdge(currentNode, up);
//                    }
//                    if (i < numOfRows - jump) {
//                        Node down = graph.getNode(i + jump, j);
//                        graph.addEdge(currentNode, down);
//                    }
//                }
//
//                // Diagonal connections considering 'k' for jumping
//                for (int jump = 1; jump <= jumpBlock; jump++) {
//                    if (i > jump && j > jump) {
//                        Node upLeft = graph.getNode(i - jump, j - jump);
//                        graph.addEdge(currentNode, upLeft);
//                    }
//                    if (i > jump && j < numOfColumns - jump) {
//                        Node upRight = graph.getNode(i - jump, j + jump);
//                        graph.addEdge(currentNode, upRight);
//                    }
//                    if (i < numOfRows - jump && j > jump) {
//                        Node downLeft = graph.getNode(i + jump, j - jump);
//                        graph.addEdge(currentNode, downLeft);
//                    }
//                    if (i < numOfRows - jump && j < numOfColumns - jump) {
//                        Node downRight = graph.getNode(i + jump, j + jump);
//                        graph.addEdge(currentNode, downRight);
//                    }
//                }
            }
        }

//        graph.printGraph();

        int shortestTime = dijkstra(graph, startRow, startColumn, finishRow, finishColumn);
//        System.out.println("Shortest time: " + shortestTime);
        System.out.println(shortestTime);

    }

    private static List<Integer> getStartFinish(String inputStartFinish){

        // for debugging purpose
//        System.out.println("inputStartFinish:" + inputStartFinish);

        List<Integer> start_finish_coordinates = new ArrayList<>();

        /*
         * use regex to scan
         * - `\\` is opening and closing parentheses
         * - `\\d+` is match one or more digit
         * - `,` is ,
         * - `\\s` is space
         * */
        Pattern pattern = Pattern.compile("\\((\\d+),\\s(\\d+)\\)");

        Matcher matcher = pattern.matcher(inputStartFinish);

        if (matcher.find()) {
            start_finish_coordinates.add(Integer.parseInt(matcher.group(1)));
            start_finish_coordinates.add(Integer.parseInt(matcher.group(2)));
        }

        if (matcher.find()) {
            start_finish_coordinates.add(Integer.parseInt(matcher.group(1)));
            start_finish_coordinates.add(Integer.parseInt(matcher.group(2)));
        }

        return start_finish_coordinates;
    }

    private static int dijkstra(Graph graph, int startRow, int startColumn, int finishRow, int finishColumn) {
        // Calculate the total number of nodes in the graph
        int totalNodes = graph.numOfColumns * graph.numOfRows;

        /*
         * Priority queue to store nodes with their distances from the start node
         * - specifies initial capacity as totalNodes
         * */
        PriorityQueue<Node> minHeap = new PriorityQueue<>(totalNodes, Comparator.comparingInt(node -> node.value));

        // Declare an array to store distances from the start node to each node in the graph
        int[] distances = new int[totalNodes];
        Arrays.fill(distances, Integer.MAX_VALUE); // Initialize all distances to infinity

        startRow--;
        startColumn--;
        finishRow--;
        finishColumn--;

//        System.out.println("startRow: " + startRow);
//        System.out.println("startColumn: " + startColumn);
//        System.out.println("finishRow: " + finishRow);
//        System.out.println("finishColumn: " + finishColumn);
//        System.out.println("graph.numOfColumns: " + graph.numOfColumns);
//        System.out.println("graph.numOfRows: " + graph.numOfRows);


        // Calculate indices of start and finish nodes in a 1D representation of the graph
        int startIndex = startRow * graph.numOfColumns + startColumn;
        int finishIndex = finishRow * graph.numOfColumns + finishColumn;

//        // for debugging purpose
//        System.out.println("startIndex: " + startIndex);
//        System.out.println("finishIndex: " + finishIndex);

        // Add the start node (source node) to the priority queue and set its distance to 0
        minHeap.add(new Node(startRow, startColumn, 0, false));
        distances[startIndex] = 0;

        // settle and unsettle set??

        // Dijkstra's algorithm
        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll(); // Extract node with minimum distance
            int currentIndex = current.row * graph.numOfColumns + current.column;

//            System.out.println("Current Index: " + currentIndex);

            // If the current node is the target node, return its distance from the start node
            if (currentIndex == finishIndex) {
                return distances[currentIndex];
            }

            /*
             * Explore neighbors of the current node
             * the adjacency list will only have a total of n * m
             * current index cannot > (n * m)
             * */
            for (Node neighbor : graph.adjLists.get(currentIndex)) {
                int neighborIndex = neighbor.row * graph.numOfColumns + neighbor.column;    // neighbourIndex in the ArrayList

                int newDistance = distances[currentIndex] + neighbor.value;

//                System.out.println("New distance: " + newDistance);

                // If a shorter path to the neighbor is found, update its distance and add to the priority queue
                if (newDistance < distances[neighborIndex]) {
                    distances[neighborIndex] = newDistance;
                    minHeap.add(new Node(neighbor.row, neighbor.column, newDistance, false));
                }
            }
        }

        return -1; // If no path found
    }
}

class Node{

    // declare the data which will be stored in the node
    int row;
    int column;
    int value;
    boolean blocked;

    //constructor
    Node(int row, int column, int value, boolean blocked){
        this.row = row;
        this.column = column;
        this.value = value;
        this.blocked = blocked;
    }
    // bro code example
//    int data;
//
//    // constructor
//    Node(int data){
//        this.data = data;
//    }
}
class Graph{
    ArrayList<LinkedList<Node>> adjLists;
    int numOfRows, numOfColumns;

    // constructor
    Graph(int numOfRows, int numOfColumns){
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        adjLists = new ArrayList<>();

//        initializeAllNode(numOfRows, numOfColumns);
    }

    /**
     * whenever we create a new node, we will also create a new LinkedList
     * The new node will be at the head of the new LinkedList
     * @param node
     */
    public void addNode(Node node){
        LinkedList<Node> currentList = new LinkedList<>();
        currentList.add(node);  // add a node to the new LinkedList
        adjLists.add(currentList);  // lastly, add the LinkedList to the ArrayList
    }

    public void initializeAllNode(int numOfRows, int numOfColumns){
        int totalNodes = numOfRows * numOfColumns;
        for(int i = 0; i <= totalNodes; i++){
            adjLists.add(new LinkedList<>());   // create a LinkedList for every node
        }
    }

    // version 1
//    public void addEdge(int source, int destination){
//        LinkedList<Node> currentList = adjLists.get(source);
//        Node destinationNode = adjLists.get(destination).get(0);    // get 0 is the head of the LinkedList
//        currentList.add(destinationNode);   // taking a node and adding it to the tail of the linkedlist
//    }

    public boolean checkEdge(int source, int destination){
        LinkedList<Node> currentList = adjLists.get(source);
        Node destinationNode = adjLists.get(destination).get(0);    // get 0 is the head of the LinkedList

        // iterate over every node in our current LinkedList
        for(Node node : currentList){
            if(node == destinationNode){
                return true;
            }
        }
        return false;
    }

    public void printGraph(){
        for(LinkedList<Node> currentList : adjLists){
            for(Node node : currentList){
                System.out.print("(" + node.row + "," + node.column + ")" + " -> ");
            }
            System.out.println();
        }
    }

    /**
     * Add edge between two adjacent nodes in the maze in the `adjLists`
     *
     * @param c1 Cell 1
     * @param c2 Cell 2
     */
    public void addEdge(Node c1, Node c2) {
        int time = 1 + Math.abs(c1.value - c2.value);

        // Convert coordinates to index
        int c1Index = c1.row * numOfColumns + c1.column;
        int c2Index = c2.row * numOfColumns + c2.column;

//        System.out.println("In `addEdge`: c1Index: " + c1Index);
//        System.out.println("In `addEdge`: c2Index: " + c2Index);

        // Add c2 to the adjacency list of c1 and vice versa
        if (isValidIndex(c1Index) && isValidIndex(c2Index)) {
            adjLists.get(c1Index).add(new Node(c2.row, c2.column, time, c2.blocked));
//            adjLists.get(c2Index).add(new Node(c1.row, c1.column, time));
        }

        // version 1
//    public void addEdge(int source, int destination){
//        LinkedList<Node> currentList = adjLists.get(source);
//        Node destinationNode = adjLists.get(destination).get(0);    // get 0 is the head of the LinkedList
//        currentList.add(destinationNode);   // taking a node and adding it to the tail of the linkedlist
//    }
    }

    // Get node from the adjacency list based on row and column indices
    public Node getNode(int row, int column) {
        /*
         * Convert 2D-array index to 1D-array index
         * row (x) * number of columns + column (y)
         * */
        int index = row * numOfColumns + column;
        if (isValidIndex(index)) {
            LinkedList<Node> list = adjLists.get(index);
            return list.isEmpty() ? null : list.getFirst();
        }
        return null;
    }

    // Check if the index is valid within the adjacency list
    private boolean isValidIndex(int index) {
        return index >= 0 && index < numOfRows * numOfColumns;
    }
}
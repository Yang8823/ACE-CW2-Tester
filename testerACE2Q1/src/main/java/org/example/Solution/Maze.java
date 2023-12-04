package org.example.Solution;/*
* Name: Tan Yee Yang
* Student ID: 20414023
* */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FibonacciNode{
    FibonacciNode parent;
    FibonacciNode child;
    FibonacciNode left;
    FibonacciNode right;
    int key;    // the value associated with the node
    int degree; // the number of its children. (Node x -> degree = Number of x's children)

    /*
    * Mark-attribute for node
    * - TRUE: node has lost one child
    * - FALSE: node has not lost any of its children
    * Newly created node is marked as FALSE
    * */
    boolean marked;
}

class FibonacciHeap{
    FibonacciNode minNode;
}

public class Maze {

    // create the min pointer as "mini"
    static FibonacciNode mini = null;

    // declare an int for the number of nodes in the heap
    static int no_of_nodes = 0;

    // function to insert a node in heap
    static void insertion(int val){
        FibonacciNode new_node = new FibonacciNode();
        new_node.key = val;
        new_node.parent = null;
        new_node.left = new_node;
        new_node.right = new_node;

        if (mini != null){
            (mini.left).right = new_node;
            new_node.right = mini;
            new_node.left = mini.left;
            mini.left = new_node;

            if(new_node.key < mini.key){
                mini = new_node;
            }
        } else {
            mini = new_node;
        }
    }

    // function to display the heap
    static void display(FibonacciNode mini){
        FibonacciNode ptr = mini;
        if(ptr == null){
            System.out.println("The Heap is Empty");
        } else {
            System.out.println("The root nodes of Heap are:");
            do{
                System.out.print(ptr.key);
                ptr = ptr.right;
                if(ptr != mini){
                    System.out.print("-->");
                }
            } while (ptr != mini && ptr.right != null);
            System.out.println();
            System.out.println("The heap has " + no_of_nodes + " nodes");
        }
    }

    // function to find min node in the heap
    static void findMin(FibonacciNode mini){
        System.out.println("min of heap is: " + mini.key);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Create Scanner object
//        Scanner input = new Scanner(new File("C:\\Users\\tanye\\Downloads\\ACE_Coursework2\\Maze\\src\\testCase3.txt")); // Create Scanner object

        int numOfColumns;    // `n` indicating the number of columns of the grid
        int numOfRows;      // `m` indicating the number of rows of the gird

        numOfColumns = input.nextInt(); // scan for `n`
        numOfRows = input.nextInt();    // scan for `m`

            // For debugging purpose
//            System.out.println("Number of Columns: " + numOfColumns);
//            System.out.println("Number of Rows: " + numOfRows);

        input.nextLine();

        String inputStartFinish = input.nextLine();
        List<Integer> start_finish_coordinates = getStartFinish(inputStartFinish);

        int startColumn = start_finish_coordinates.get(0);
        int startRow = start_finish_coordinates.get(1);
        int finishColumn= start_finish_coordinates.get(2);
        int finishRow = start_finish_coordinates.get(3);

//        input.next();
//        int startRow = input.nextInt();
////        input.next();
//        int startColumn = input.nextInt();
//        input.next();
//
//            // for debugging purpose
//            System.out.println("Start Cell: " + startRow + "," + startColumn);
//
//        int finishRow = input.nextInt();
//        int finishColumn = input.nextInt();

            // For debugging purpose
//            System.out.println("Start Cell: " + startRow + "," + startColumn);
//            System.out.println("Finish Cell: " + finishRow + "," + finishColumn);

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

        for(int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfColumns; j++){
                int value = input.nextInt();
                Node currentNode = new Node(i, j, value);

                graph.addNode(currentNode);
            }
        }

//        graph.printGraph();

        for(int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfColumns; j++){
                Node currentNode = graph.getNode(i,j);

                // Connect to adjacent cells (up, down, left, right)
                if (i > 0) {
//                    Node up = new Node(i - 1, j, value);
//                    graph.addNode(up);

                    Node up = graph.getNode(i -1, j);
                    graph.addEdge(currentNode, up);
                }
                if (i < numOfRows - 1) {
//                    Node down = new Node(i + 1, j, value);
//                    graph.addNode(down);

                    Node down = graph.getNode(i + 1, j); // Get the cell below
                    graph.addEdge(currentNode, down); // Connect current cell to the cell below
                }
                if (j > 0) {
//                    Node left = new Node(i, j - 1, value);
//                    graph.addNode(left);

                    Node left = graph.getNode(i, j - 1); // Get the cell on the left
                    graph.addEdge(currentNode, left);
                }
                if (j < numOfColumns - 1) {
//                    Node right = new Node(i, j + 1, value);
//                    graph.addNode(right);

                    Node right = graph.getNode(i, j + 1); // Get the cell on the right
                    graph.addEdge(currentNode, right);
                }
            }
        }

//        graph.printGraph();

        int shortestTime = dijkstra(graph, startRow, startColumn, finishRow, finishColumn);
//        System.out.println("Shortest time: " + shortestTime);
        System.out.println(shortestTime);

//        no_of_nodes = 7;
//        insertion(4);
//        insertion(3);
//        insertion(7);
//        insertion(5);
//        insertion(2);
//        insertion(1);
//        insertion(10);
//
//        display(mini);
//
//        findMin(mini);

//        Graph graph = new Graph();
//
//        graph.addNode(new Node(1,1));
//        graph.addNode(new Node(2,1));
//        graph.addNode(new Node(3,1));
//        graph.addNode(new Node(4,1));
//        graph.addNode(new Node(5,1));
//
//        graph.addEdge(0, 1);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 4);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(4, 0);
//        graph.addEdge(4, 2);
//
//        graph.printGraph();

    }

    private static List<Integer> getStartFinish(String inputStartFinish){

            // for debugging purpose
//            System.out.println("inputStartFinish:" + inputStartFinish);

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

        // for debugging purpose
//        System.out.println("startIndex: " + startIndex);
//        System.out.println("finishIndex: " + finishIndex);

        // Add the start node (source node) to the priority queue and set its distance to 0
        minHeap.add(new Node(startRow, startColumn, 0));
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
                    minHeap.add(new Node(neighbor.row, neighbor.column, newDistance));
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

    //constructor
    Node(int row, int column, int value){
        this.row = row;
        this.column = column;
        this.value = value;
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

        // Add c2 to the adjacency list of c1 and vice versa
        if (isValidIndex(c1Index) && isValidIndex(c2Index)) {
            adjLists.get(c1Index).add(new Node(c2.row, c2.column, time));
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

class Dijkstra{
    int startRow, startColumn, finishRow, finishColumn;
    Graph graph;
    Dijkstra(Graph graph, int startRow, int startColumn, int finishRow, int finishColumn){
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.finishRow = finishRow;
        this.finishColumn = finishColumn;
        this.graph = graph;
    }

    public static Graph calculateShortestPath(Graph graph, Node sourceNode, int startRow, int startColumn, int finishRow, int finishColumn){
        int totalNodes = graph.numOfRows * graph.numOfColumns;

        Set<Node> settledNodes = new HashSet<>();   // visited nodes set
        PriorityQueue<Node> unsettledNodes = new PriorityQueue<>(totalNodes, Comparator.comparingInt(node -> node.value)); // unvisited nodes set
//        PriorityQueue<Node> unsettledNodes = new PriorityQueue<>(totalNodes, new Node()); // unvisited nodes set

        int[] distances = new int[totalNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);  // fill the distance of all nodes with infinity

        int sourceIndex = startRow * graph.numOfColumns + startColumn;
        int destinationIndex = finishRow * graph.numOfColumns + finishColumn;

        unsettledNodes.add(new Node(startRow, startColumn, 0));
        distances[sourceIndex] = 0;




        return graph;
    }

}
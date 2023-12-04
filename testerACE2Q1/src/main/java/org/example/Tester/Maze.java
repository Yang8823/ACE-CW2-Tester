package org.example.Tester;

import java.io.FileNotFoundException;
import java.util.*;

public class Maze {
    public static void main(String[] args) {
        String regex = "[(),\\s]+";

        Scanner scanner = new Scanner(System.in);
        int columns = scanner.nextInt();
        int rows = scanner.nextInt();

        scanner.nextLine();
        String[] input = scanner.nextLine().split(regex);
        int inputX = Integer.parseInt(input[1]);
        int inputY = Integer.parseInt(input[2]);
        int destX = Integer.parseInt(input[3]);
        int destY = Integer.parseInt(input[4]);

        int startX = inputY - 1;
        int startY = inputX - 1;
        int endX = destY - 1;
        int endY = destX - 1;

        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int shortestTime = findShortestTime(matrix, startX, startY, endX, endY);
        System.out.println(shortestTime);
    }

    public static int findShortestTime(int[][] matrix, int startX, int startY, int endX, int endY) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        priorityQueue.add(new int[]{startX, startY, 0});

        int[][] initialCost = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(initialCost[i], Integer.MAX_VALUE);
        }
        initialCost[startX][startY] = 0;

        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            int currentRow = current[0];
            int currentCol = current[1];

            if (currentRow == endX && currentCol == endY) {
                return current[2];
            }

            for (int[] direction : directions) {
                int newRow = currentRow + direction[0];
                int newCol = currentCol + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    int newCost = current[2] + calculateDifference(matrix[currentRow][currentCol], matrix[newRow][newCol]);
                    if (newCost < initialCost[newRow][newCol]) {
                        initialCost[newRow][newCol] = newCost;
                        priorityQueue.add(new int[]{newRow, newCol, newCost});
                    }
                }
            }
        }

        return -1;
    }

    public static int calculateDifference(int a, int b) {
        return 1 + Math.abs(a - b);
    }


}

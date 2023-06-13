package pathFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String str = "abcdefghj";
        String a = ".W.\n" +
                ".W.\n" +
                "...";
        String b =  b = ".W.\n"+
                ".W.\n"+
                "W..";

        //String[][] arr = converter(a);
        String[][] arr = converter(b);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        System.out.println("intarr:");
        int[][] intArr = arrayConverter(arr);
        for (int i = 0; i < intArr.length; i++) {
            for (int j = 0; j < intArr[i].length; j++) {
                System.out.print(intArr[i][j]);
            }
            System.out.println();
        }
        int[] oneDArr = convertToOneDArray(intArr);
        int[][] room = new int[intArr.length + 2][intArr[0].length+2];
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[0].length; j++) {
                if (i == 0 || i == room.length - 1 || j == 0 || j == room[0].length - 1) {
                    room[i][j] = 1; //walls
                }
            }
        }
        int n = 0;
        while (n <= arr.length-1){

            for (int i = 1; i < room.length - 1; i++) {
                for (int j = 1; j < room[i].length - 1; j++) {
                    //System.out.println("arr[n]: "+arr[n]);
                    room[i][j] = convertToOneDArray(intArr)[n];
                    n++;
                }
            }
        }
        System.out.println();
        System.out.println("room");
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                System.out.print(room[i][j]);
            }
            System.out.println();
        }

        System.out.println("-------maze------");
        // maze:
        int[][] maze =

                {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                        {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}


                };
        System.out.println();
        System.out.println("original maze: ");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();

        //Egymás után nem futtathatóak!!!
        //findPath(maze, new Node(1, 1), new Node(1, 8));
        findPath(maze, new Node(1, 1), new Node(11, 8));
        //findPath(maze, new Node(1, 1), new Node(11, 8));
        //findPath(maze, new Node(1, 1), new Node(1, 8));
        //findPath(room, new Node(1, 1), new Node(3, 3));

    }


    public static void drawMaze(int[][] maze, List<Node> path) {
        System.out.println("--------SOLUTION---------");
        for (int i = 0; i < path.size(); i++) {

            maze[path.get(i).y()][path.get(i).x()] = 2;
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {

                if (maze[i][j] == 2) {
                    System.out.print("\u001b[1;32m" + maze[i][j] + "\u001b[0m" + " "); //green
                } else {
                    System.out.print(maze[i][j] + " ");
                }

            }
            System.out.println();
        }
        System.out.println("path's coordinates: ");
        Collections.reverse(path);
        // list of solve coordinates
        for (int i = 0; i < path.size(); i++) {
            System.out.print("(" + path.get(i).x + " " + path.get(i).y + ")" + " ");

        }
        System.out.println();
        System.out.println("path size: " + path.size());

    }



    public static void findPath(int[][] maze, Node startNode, Node goalNode) {
        int x = startNode.x;
        int y = startNode.y;
        int[][] copiedMaze = copy(maze);
        List<Node> path = new ArrayList<>();
        if (searchPath(maze, x, y, path, goalNode)) { // start coordinate: (x,y)
            System.out.println("There is a solution:\n");
            drawMaze(copiedMaze, path);
        } else {
            System.out.println("No solution.");
        }
    }

    //2D array copy
    static int[][] copy(int[][] array) {
        int[][] copy = new int[array.length][array[0].length];
        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                copy[row][column] = array[row][column];
            }
        }
        return copy;

    }


    //---------------------------Searching algorithm------------------------------

    public static boolean searchPath(int[][] maze, int x, int y, List<Node> path, Node goalNode) {

        //maze[goalNode.y][goalNode.x] = 9; //goalnodet- elállítja, nem jo
        System.out.println("maze[goalNode.y][goalNode.x]: "+maze[goalNode.y][goalNode.x]);
        System.out.println("goalNode.x: "+goalNode.x);
        System.out.println("goalNode.y: "+goalNode.y);


        //if (maze[y][x] == maze[goalNode.y][goalNode.x]) { //?// == 9 volt
        if (y== goalNode.y && x ==goalNode.x && maze[y][x] !=1) { //?
            Node node = new Node(x, y);
            path.add(node);
            return true;
        }


        if (maze[y][x] == 0) {
            maze[y][x] = 2; //?

            if (searchPath(maze, x - 1, y, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x + 1, y, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y - 1, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y + 1, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }

        }

        return false;
    }

    // Node class

    private static record Node(int x, int y) {
    }

    /*
    private static class Node{
        private int x;
        private int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    */
    public static String[][] converter(String str) {

        return Arrays.stream(str.split("\\n"))
                .map(e -> Arrays.stream(e.split(""))
                        .toArray(String[]::new)).toArray(String[][]::new);
    }

    public static int[][] arrayConverter(String[][] input) {
        System.out.println("input.length: "+input.length);
        int[][] arr = new int[input.length][input[0].length];
        for (int row = 0; row < input.length; row++) {
            for (int column = 0; column < input[0].length; column++) {
                if (input[row][column].equals("W")) {
                    arr[row][column] = 1;
                } else if (input[row][column].equals(".")){
                    arr[row][column] = 0;
                }
            }
        }
        return arr;
    }

    public static int[] convertToOneDArray(int[][] matrix){
        return Stream.of(matrix).flatMapToInt(IntStream::of).toArray();
    }


}

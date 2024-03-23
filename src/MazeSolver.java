import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class MazeSolver {
    public MazeSolver(){}

    public static void printList(ArrayList<Integer[]> b){
        for (int coords = 0; coords < b.size() - 1; coords++){
            String[] coordString = new String[2];
            int idx = 0;
            for (int i = 0; i < b.get(coords).length; i++){
                coordString[idx] = "" + b.get(coords)[i];
                idx++;
            }
            System.out.print("(" + coordString[0] + ", " + coordString[1] + ")-->");
        }
        System.out.println("(" + b.getLast()[0] + ", " + b.getLast()[1] + ")");
    }

    // gets the "path" of the maze
    public ArrayList<Integer[]> getPath(String[][] maze){
        ArrayList<Integer[]> path = new ArrayList<>();
        for (int r = 0; r < maze.length; r++) { // rows
            for (int c = 0; c < maze[0].length; c++){ // columns
                if (maze[r][c].equals(".")){
                    path.add(new Integer[] {r, c});
                }
            }
        }
        return path;
    }

    public boolean isEnd(Integer[] coordinates, ArrayList<Integer[]> path, String[][] maze){
        if (Arrays.equals(coordinates, new Integer[] {0,0}) || Arrays.equals(coordinates, new Integer[] {maze.length - 1, maze[0].length - 1})){
            return false;
        }
        int count = 0;
        for (Integer[] c : path){
            if (isAdjacent(coordinates, c, maze)){
                count++;
            }
        }
        return count < 2;
    }

    public boolean isAdjacent(Integer[] coordinates, Integer[] c, String[][] maze){
        return ((coordinates[0] != 0 && (coordinates[0] - 1 == c[0] && coordinates[1] == c[1])) || // checks up
                (coordinates[0] != maze.length - 1 && (coordinates[0] + 1 == c[0] && coordinates[1] == c[1]))|| // checks down
                (coordinates[1] != 0 && (coordinates[1] - 1 == c[1] && coordinates[0] == c[0])) || // checks left
                (coordinates[1] != maze[0].length - 1 && (coordinates[1] + 1 == c[1] && coordinates[0] == c[0]))); // checks right
    }

    // checking to the right at the start doesn't work as (0,0) doesn't count as an adjacent space for some reason
    public ArrayList<Integer[]> solve(String[][] maze){
        ArrayList<Integer[]> path = getPath(maze);
        int endCount = 1;
        while (endCount > 0){
            endCount = 0;
            for (int i = 0; i < path.size(); i++){
                if (isEnd(path.get(i), path, maze)){
                    endCount++;
                    path.remove(i);
                    i--;
                }
            }
        }
        return sortPath(path, maze);
    }

    public ArrayList<Integer[]> sortPath(ArrayList<Integer[]> path, String[][]maze){
        int size = path.size();
        ArrayList<Integer[]> sortedList = new ArrayList<>();
        sortedList.add(path.remove(0)); // adds (0,0)
        int idx = 0;
        while (sortedList.size() < size){
            for (int i = 0; i < path.size(); i++){
                if (isAdjacent(path.get(i), sortedList.get(idx), maze)){
                    sortedList.add(path.remove(i));
                    idx++;
                    break;
                }
            }
        }
        return sortedList;
    }
}

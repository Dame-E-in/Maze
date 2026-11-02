import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Search{

    // I learned that you could do this recently
    private Search(){
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static Maze dfs(Maze maze, boolean debugMode){
        return search(maze, true, debugMode);
    }

    private static Maze search(Maze maze, boolean isDfs, boolean debugMode){
        List<int[]> visited = new ArrayList<>();
        int[] start = maze.getStart();
        int[] finish = {0,0,0};
        int dist = 0;
        Deque<int[]> staqueue = new ArrayDeque<>();

        staqueue.push(new int[] {start[0], start[1], dist});

        while (!staqueue.isEmpty()){
            int[] curr = isDfs ? staqueue.pop() : staqueue.poll();
            if (maze.isFinish(curr)){
                finish = curr;
                break;
            }
            visited.add(curr); //put after the break so finish doesn't get added to visited

            int[] above = {curr[0] - 1, curr[1], curr[2] + 1};
            int[] below = {curr[0] + 1, curr[1], curr[2] + 1};
            int[] left = {curr[0], curr[1] - 1, curr[2] + 1};
            int[] right = {curr[0], curr[1] + 1, curr[2] + 1};

            // .scan() only returns true on unvisited coords, so no need to worry about visited yet
            if (maze.scan(above)){
                if (isDfs) staqueue.push(above);
                else staqueue.offer(above);
            }
            if (maze.scan(right)){
                if (isDfs) staqueue.push(right);
                else staqueue.offer(right);
            }
            if (maze.scan(below)){
                if (isDfs) staqueue.push(below);
                else staqueue.offer(below);
            }
            if (maze.scan(left)){
                if (isDfs) staqueue.push(left);
                else staqueue.offer(left);
            }
        }

        if (debugMode) System.out.println(maze.toString());

        int[] closest_neighbor;
        int[] current_point = finish;
        int[] check_point;


        // You said yes to HashMaps, but I only asked for that one instance.
        // Will make this a hell of a lot prettier if I get permission to use one here too.

        while(true){
        closest_neighbor = current_point;
            for (int i = visited.size() - 1; i >= 0; i--){
                check_point = visited.get(i);

                // Check left
                if ((current_point[0] == check_point[0]) &&
                    (current_point[1] - 1 == check_point[1])){

                    if(check_point[2] < closest_neighbor[2]){
                        closest_neighbor = check_point;
                    }
                }

                // Check right
                if ((current_point[0] == check_point[0]) &&
                    (current_point[1] + 1 == check_point[1])){
                    if(check_point[2] < closest_neighbor[2]){
                        closest_neighbor = check_point;
                    }
                }

                // Check above
                if ((current_point[0] - 1 == check_point[0]) &&
                    (current_point[1] == check_point[1])){
                    if(check_point[2] < closest_neighbor[2]){
                        closest_neighbor = check_point;
                    }
                }

                // Check below
                if ((current_point[0] + 1 == check_point[0]) &&
                    (current_point[1] == check_point[1])){
                    if(check_point[2] < closest_neighbor[2]){
                        closest_neighbor = check_point;
                    }
                }
            }
            current_point = closest_neighbor;
            if ((current_point[0] == start[0]) &&
                (current_point[1] == start[1])){
                if (debugMode) System.out.println("Break!");
                break;
            }else{
                if (debugMode) System.out.println("Paving: " + current_point[0] + ", " + current_point[1]);
                if (!maze.pave(current_point)){
                    System.err.println("Can't pave wall!");
                    break;
                }
            }
        }
        if (debugMode) System.out.println(maze.toString());
        return maze;
    }
    
    public static Maze bfs(Maze maze, boolean debugMode){
        return search(maze, false, debugMode);
    }
}

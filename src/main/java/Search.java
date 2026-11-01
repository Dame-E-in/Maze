import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Search{
    static List<int[]> visited = new ArrayList<>();

    public static Maze dfs(Maze maze, boolean debugMode){
        int[] start = maze.getStart();

        int[] finish = {0, 0, 0};
        Deque<int[]> s = new ArrayDeque<>();

        // prime stack
        int dist = 0;
        s.push(new int[] {start[0], start[1], dist});

        while (!s.isEmpty()){
            int[] curr = s.pop();
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
                s.push(above);
            }
            if (maze.scan(right)){
                s.push(right);
            }
            if (maze.scan(below)){
                s.push(below);
            }
            if (maze.scan(left)){
                s.push(left);
            }
        }

        System.out.println(maze.toString());

        int[] closest_neighbor;
        int[] current_point = finish;
        int[] check_point;
        
        //for (int path = finish[2]; path > 0; path--){

        while(true){
        // missed opportunity to call it nearest neighbor :(
        // I already wrote the code, not changing it now
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
        return maze;
    }
    
    public static Maze bfs(Maze maze, boolean debugMode){
        // Following WET instead of DRY here for quality of life reasons

        int[] start = maze.getStart();
        int[] finish = {0,0,0};

        int dist = 0;
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {start[0], start[1], dist});

        while (!q.isEmpty()){
            int[] curr = q.poll();
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
                q.offer(above);
            }
            if (maze.scan(right)){
                q.offer(right);
            }
            if (maze.scan(below)){
                q.offer(below);
            }
            if (maze.scan(left)){
                q.offer(left);
            }
        }

        int[] closest_neighbor;
        int[] current_point = finish;
        int[] check_point;
        
        //for (int path = finish[2]; path > 0; path--){

        while(true){
        // missed opportunity to call it nearest neighbor :(
        // I already wrote the code, not changing it now
        closest_neighbor = current_point;
            for (int i = 0; i < visited.size(); i++){
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
        return maze;
    }
}

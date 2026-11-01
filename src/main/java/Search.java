import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Search{

    public static Maze dfs(Maze maze){
        List<int[]> visited = new ArrayList<>();


        int[] start = maze.getStart();

        // finish being set to (0,0) can crash it
        // At really big maze sizes (e.g. 1500), DFS can't find the finish
        // I left it like this since if it doesn't find a finish it probably should crash

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
                System.out.println("Break!");
                break;
            }else{
                System.out.println("Paving: " + current_point[0] + ", " + current_point[1]);
                if (!maze.pave(current_point)){
                    System.err.println("Can't pave wall!");
                    break;
                }
            }
        }
        return maze;
    }
    
    public static void bfs(Maze maze){
        int[] start = maze.getStart();
        Deque<String> q = new ArrayDeque<>();
    }
}

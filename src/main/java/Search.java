import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Search{
    public static void dfs(Maze maze){
        List<int[]> visited = new ArrayList<>();


        int[] start = maze.getStart();
        int[] finish;
        Deque<int[]> s = new ArrayDeque<>();

        // prime stack
        int dist = 0;
        s.push(new int[] {start[0], start[1], dist});

        while (!s.isEmpty()){
            int [] curr = s.pop();
            if (maze.isFinish(curr)){
                System.out.println("Finish located!");
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
    }
    
    public static void bfs(Maze maze){
        int[] start = maze.getStart();
        Deque<String> q = new ArrayDeque<>();
    }
}

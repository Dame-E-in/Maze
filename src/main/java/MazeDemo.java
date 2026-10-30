public class MazeDemo {
    public static void main(String[] args) {
        System.out.println("Printing debug to console.");

        String mazeSize;
        Object[] options = MazeGui.MazeDialog();

        String dpi = (String) options[0];
        int size = (int) options[1];

        System.out.println("DPI choice: " + dpi);
        System.out.println("Maze Size: " + size);


        if (size > 25){
            mazeSize = "big";
        }else{
            mazeSize = "small";
        }

        Maze maze1 = new Maze(size);
        System.out.println(maze1);
        
        MazeGui gui = new MazeGui("Maze Demo", dpi, mazeSize);
        gui.println(maze1.toString());
    }

}
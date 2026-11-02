import javax.swing.JOptionPane;

public class MazeDialog extends Config{

    public MazeDialog(String dpi, int size, String search_alg){
        this.dpi = dpi;
        this.size = size;
        this.search_alg = search_alg;

        MazeConfig();
    }

    private void MazeConfig(){

        String[] dpiOptions = {"High DPI", "Low DPI"};

        int dpiChoice = JOptionPane.showOptionDialog(
            null,
            "Select your DPI settings:",
            "DPI settings",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            dpiOptions,
            dpiOptions[0]
        );
        
        if (dpiChoice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        String sizeInput = JOptionPane.showInputDialog(
            null,
            "Enter maze size (leave blank for 25):",
            "Maze Size",
            JOptionPane.QUESTION_MESSAGE
        );

        if (sizeInput == null) {
            System.exit(0);
        }

        try {
            if (sizeInput.isEmpty()){ 
                size = 25;
            }else{
                size = Integer.parseInt(sizeInput.trim());
            }

            if (size <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null, 
                "Whoa nelly. Please use a positive number.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }

        if (dpiChoice == 0){
            dpi = "high_dpi";
        }else{
            dpi = "low_dpi";
        }
        String[] search_options = {"Breadth First Search", "Depth First Search"};

    int search_alg_choice = JOptionPane.showOptionDialog(
        null,
        "Which search algorithm would you like to use?",
        "Search Settings",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        search_options,
        search_options[0]
    );

    if (search_alg_choice == 0){
            search_alg = "bfs";
        }else{
            search_alg = "dfs";
        }
    }
}

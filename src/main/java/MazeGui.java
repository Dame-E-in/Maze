import java.awt.Font;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MazeGui{
    private JFrame frame;
    private JTextArea textArea;
    private String mazeSize;

    public MazeGui(String title, String dpi, int size){
        if (size > 25){
            this.mazeSize = "big";
        }else{
            this.mazeSize = "small";
        }

        // Probably need to fix this disaster since it's broken on Windows.
        // Added for my own sanity, Xfce hates properly scaling Swing GUIs.
        int HIGH_DPI_WIDTH = 1000;
        int HIGH_DPI_HEIGHT = 1000;
        int HIGH_DPI_FONT = 76;
        double HIGH_DPI_XY = 1.06;
        double HIGH_DPI_XZ = .325;

        int LOW_DPI_WIDTH = 500;
        int LOW_DPI_HEIGHT = 500;
        int LOW_DPI_FONT = 34;
        double LOW_DPI_XY = 1.032;
        double LOW_DPI_XZ = .33;

        if ("low_dpi".equals(dpi)){ 
            generate(title, LOW_DPI_WIDTH, LOW_DPI_HEIGHT, LOW_DPI_FONT, LOW_DPI_XY, LOW_DPI_XZ);
        }else if ("high_dpi".equals(dpi)){
            generate(title, HIGH_DPI_WIDTH, HIGH_DPI_HEIGHT, HIGH_DPI_FONT, HIGH_DPI_XY, HIGH_DPI_XZ);
        }else{
            System.err.println("Invalid perameter for GUI DPI. Please enter \"high_dpi\" or \"low_dpi\".");
            System.exit(1);
        }
    }

    public void generate(String title, int width, int height, int fontSize, double xy, double xz){
        SwingUtilities.invokeLater(() -> {
        this.frame = new JFrame(title);
        textArea = new JTextArea();
        // EXIT_ON_CLOSE used because the default is to send it to the background
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //textArea.setMargin(new Insets(10, 10, 10, 10));

        this.frame.setSize(width, height);

        // Might not need
        Font baseFont = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);

        AffineTransform transform = new AffineTransform();

        transform.scale(xy, xz);

        Font squareFont = baseFont.deriveFont(transform);

        this.textArea.setFont(squareFont);
        textArea.setEditable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        
        if ("big".equals(this.mazeSize)) {
            this.frame.add(new JScrollPane(textArea));
        }else if ("small".equals(this.mazeSize)){
            this.frame.setResizable(false);
            this.frame.add(textArea);
        }else{
            System.err.println("Invalid perameter for GUI maze size. Please enter \"small\" or \"big\".");
            System.exit(1);
        }
        });
    }

    public void print(String text){
        SwingUtilities.invokeLater(() -> {
            if (this.textArea != null) {
                this.textArea.append(text);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }

    public void println(String text){
        this.print(text + "\n");
    }

}
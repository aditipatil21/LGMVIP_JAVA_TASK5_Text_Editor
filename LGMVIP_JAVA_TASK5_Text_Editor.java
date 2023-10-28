import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter; 

public class Text_Editor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel font_label;
    JSpinner fontSizeSpinner;
    JButton fontColourButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem open_item;
    JMenuItem save_item;
    JMenuItem exit_item;

    Text_Editor() {
    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simple text editor");
        this.setSize(800, 800);
        this.setLayout(new FlowLayout());

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(560, 560));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Georgia", Font.PLAIN, 15));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(560, 560));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        font_label = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(60, 30));
        fontSizeSpinner.setValue(15);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
           
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
            
        });

        fontColourButton = new JButton("Colour");
        fontColourButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Georgia");

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        open_item = new JMenuItem("Open");
        save_item = new JMenuItem("Save");
        exit_item = new JMenuItem("Exit");

        open_item.addActionListener(this);
        save_item.addActionListener(this);
        exit_item.addActionListener(this);

        menu.add(open_item);
        menu.add(save_item);
        menu.add(exit_item);
        menuBar.add(menu);

        JMenu menu2 = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("cut");
        JMenuItem copy = new JMenuItem("copy");
        JMenuItem paste = new JMenuItem("paste");
 
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
 
        menu2.add(cut);
        menu2.add(copy);
        menu2.add(paste);
        menuBar.add(menu2);

        this.setJMenuBar(menuBar);
        this.add(font_label);
        this.add(fontSizeSpinner);
        this.add(fontColourButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (e.getSource() == fontColourButton) {
            JColorChooser colour_chooser = new JColorChooser();
            Color colour = colour_chooser.showDialog(null, "Choose a colour", Color.blue); 
            textArea.setForeground(colour);
        }
        if (e.getSource() == fontBox) {
            textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }
       
        if (s.equals("cut")) {
            textArea.cut();
        }
        if (s.equals("copy")) {
            textArea.copy();
        }
        if (s.equals("paste")) {
            textArea.paste();
        }
        
        if (e.getSource() == open_item) {
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            file_chooser.setFileFilter(filter);
            int response = file_chooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(file_chooser.getSelectedFile().getAbsolutePath());
                Scanner file_in = null;
                try {
                    file_in = new Scanner(file);
                    if (file.isFile()) {
                        while(file_in.hasNextLine()) {
                            String line = file_in.nextLine() + "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } 
                finally {
                    file_in.close();
                }
            }
        }
        
        if (e.getSource() == save_item) {
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setCurrentDirectory(new File("."));
            int response = file_chooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter file_out = null;
                file = new File(file_chooser.getSelectedFile().getAbsolutePath());
                try {
                    file_out = new PrintWriter(file);
                    file_out.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                finally {
                    file_out.close();
                }
            }
        }
       
        if (e.getSource() == exit_item) {
            System.exit(0);
        }
    }
}

import javax.swing.*;
import java.awt.*;
//import java.io.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
//import javax.swing.plaf.ColorChooserUI;
import javax.swing.plaf.metal.*;
//import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

public class NotePad extends JFrame implements ActionListener {
    //    JTextArea writingArea;
    JFrame frame;
    JTextArea writingArea;
    JCheckBoxMenuItem wordwrap;
    JScrollPane sc ;
    JPanel panel;
    UndoManager undoManager;
    NotePad() {
        frame = new JFrame("Note-pad");

//      Try and catch has been added to use the setLookAndFeel rather than implementing other exception handler
        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        writingArea = new JTextArea();

        undoManager = new UndoManager();
                
        writingArea.setMargin(new Insets(10, 10, 10, 10));
        writingArea.setFont(new Font("Serif", Font.ITALIC, 18));
        
        //To undo and redo the operation
        writingArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        
        sc = new JScrollPane(writingArea , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sc.setBorder(BorderFactory.createEmptyBorder());

        
        // Create a menu-bar
        JMenuBar menubar = new JMenuBar();

        // Create a menu for menubar
        JMenu m1_File = new JMenu("File");

        // Create menu items
        JMenuItem New_file = new JMenuItem("New");
        JMenuItem New_Window = new JMenuItem("New Window");
        JMenuItem Open_file = new JMenuItem("Open");
        JMenuItem Save_file = new JMenuItem("Save");
        JMenuItem Print_file = new JMenuItem("Print");

        // Add action listener
        New_file.addActionListener(this);
        Open_file.addActionListener(this);
        Save_file.addActionListener(this);
        Print_file.addActionListener(this);
        New_Window.addActionListener(this);

        m1_File.add(New_file);
        m1_File.add(Open_file);
        m1_File.add(Save_file);
        m1_File.add(Print_file);
        m1_File.add(New_Window);
        // Create a menu for editing
        JMenu m2_Edit = new JMenu("Edit");

        // Create edit menu items
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem selectAll = new JMenuItem("Select All");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        // Add action listener for edit menu items
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);

        // Added menuitems to edit menu
        m2_Edit.add(cut);
        m2_Edit.add(copy);
        m2_Edit.add(paste);
        m2_Edit.add(selectAll);
        m2_Edit.add(undo);
        m2_Edit.add(redo);

        // Create format menu
        JMenu m3_format = new JMenu("Format");

        // Create menu items for format menu
        wordwrap = new JCheckBoxMenuItem("Word Wrap");
        JMenu font = new JMenu("Font");

        //Sub menus of font
        JMenuItem textColor = new JMenuItem("Text Color");
        JMenuItem bgColor = new JMenuItem("Back-Ground Color");

        //Adding submenus of font
        font.add(textColor);
        font.add(bgColor);

        //Action listener for font's sub menu
        textColor.addActionListener(this);
        bgColor.addActionListener(this);

        // Add action listener for the format menu
        wordwrap.addActionListener(this);


        // Added the menu items of format menu
        m3_format.add(wordwrap);
        m3_format.add(font);

        // Adding View menu
        JMenu m4_view = new JMenu("View");

        // Menu items of View menu
        JMenuItem zoomIn = new JMenuItem("Zoom In");
        JMenuItem zoomOut = new JMenuItem("Zoom Out");

        // Action listners of the menuitems fo format menu
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);

        // Added the menu items of View menu
        m4_view.add(zoomIn);
        m4_view.add(zoomOut);

        // Added all menu to menu bar
        menubar.add(m1_File);
        menubar.add(m2_Edit);
        menubar.add(m3_format);
        menubar.add(m4_view);

//        panel.add(sc);
        frame.add(sc);
        frame.setJMenuBar(menubar);
//        frame.add(writingArea);
        frame.setSize(700, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        NotePad np = new NotePad();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(wordwrap.isSelected())
        {
            writingArea.setLineWrap(true);
            writingArea.setWrapStyleWord(true);
        }
        if (s.equals("Cut")) {
            writingArea.cut();
        }
        else if (s.equals("Copy")) {
            writingArea.copy();
        }
        else if (s.equals("Paste")) {
            writingArea.paste();
        }
        else if (s.equals("Select All")) {
            writingArea.selectAll();
        }
        else if (s.equals("New Window")) {
            NotePad np = new NotePad();
        }
        else if(s.equals("Undo"))
        {
            try{
                undoManager.undo();
            }
            catch(Exception ex){}

//            if(undoManager.canUndo())
//            {
//                undoManager.undo();
//            }
//            else
//            {
//                System.out.println("The undo fails");
//            }
        }
        else if(s.equals("Redo"))
        {
            try{
                undoManager.redo();
            }
            catch(Exception ex){}

//            if (undoManager.canRedo()) {
//                undoManager.redo();
//            }
//            else
//            {
//                System.out.println("The redo don't works");
//            }
        }
        else if (s.equals("Save")) {

                // Create an object of JFileChooser class
                JFileChooser j = new JFileChooser("f:");

                // Invoke the showsSaveDialog function to show the save dialog
                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {

                    // Set the label to the path of the selected directory
                    File fi = new File(j.getSelectedFile().getAbsolutePath());
                    boolean b = fi.exists();
                    if(b == false) {
                        try {
                            // Create a file writer
                            FileWriter wr = new FileWriter(fi, false);

                            // Create buffered writer to write
                            BufferedWriter w = new BufferedWriter(wr);

                            // Write
                            w.write(writingArea.getText());

                            w.flush();
                            w.close();
                        } catch (Exception evt) {
                            JOptionPane.showMessageDialog(frame, evt.getMessage());
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "The file already exists");
                }
                // If the user cancelled the operation
                else
                    JOptionPane.showMessageDialog(frame, "The user cancelled the operation");
        }
        else if (s.equals("Print")) {
            try {
                // print the file
                writingArea.print();
            }
            catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        }
        else if (s.equals("Open")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // String
                    String s1 = "", sl = "";

                    // File reader
                    FileReader fr = new FileReader(fi);

                    // Buffered reader
                    BufferedReader br = new BufferedReader(fr);

                    // Initialize sl
                    sl = br.readLine();

                    // Take the input from the file
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }

                    // Set the text
                    writingArea.setText(sl);
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(frame, "The user cancelled the operation");
        }
        else if (s.equals("New")) {
            writingArea.setText("");
        }
        else if(s.equals("Text Color"))
        {
            JColorChooser colorChoser = new JColorChooser();

            Color color = colorChoser.showDialog(null , "Pick the text color..." , Color.BLACK);
            writingArea.setForeground(color);
        }
        else if(s.equals("Back-Ground Color"))
        {
            JColorChooser colorChoser = new JColorChooser();

            Color color = colorChoser.showDialog(null , "Pick the Back-ground color..." , Color.BLACK);
            writingArea.setBackground(color);
        }
//        else {
//            writingArea.setText("The menu button pressed is : " + s);
//        }
    }
}

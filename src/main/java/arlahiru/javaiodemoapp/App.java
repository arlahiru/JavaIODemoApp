package arlahiru.javaiodemoapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

public class App {

    JFrame frame = new JFrame("Java I/O Demo App");
    JTextArea txtAreaFileContent = new JTextArea(10, 10);
    JButton btnBrowse = new JButton("Browse File");
    JButton btnSave = new JButton("Save File");
    JScrollPane fileContentPane = new JScrollPane();

    public App() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 5, 5));

        txtAreaFileContent.setWrapStyleWord(true);
        txtAreaFileContent.setLineWrap(true);
        txtAreaFileContent.setMargin(new Insets(3, 3, 3, 3));

        fileContentPane.setViewportView(txtAreaFileContent);

        fileContentPane.setBorder(
                BorderFactory.createTitledBorder("File Content"));

        centerPanel.add(fileContentPane);

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(btnBrowse);

        bottomPanel.add(btnSave);

        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FileReader freader = null;
                BufferedReader breader = null;
                String fileContent = "";
                String line = "";
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                        }

                        @Override
                        public String getDescription() {
                            return "*.txt";
                        }
                    });
                    int option = fileChooser.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {

                        File selectedFile = fileChooser.getSelectedFile();
                        freader = new FileReader(selectedFile);

                        breader = new BufferedReader(freader);
                        while ((line = breader.readLine()) != null) {
                            fileContent += line + "\n";
                        }

                        txtAreaFileContent.setText(fileContent);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    try {
                        if (freader != null) {
                            freader.close();
                        }
                        if (breader != null) {
                            breader.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String text = txtAreaFileContent.getText();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "*.txt";
                    }
                });
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    PrintWriter pw = null;
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        String fileName = selectedFile.getName();
                        if (!fileName.endsWith(".txt")) {

                            fileName = fileName.concat(".txt");

                            selectedFile = new File(fileChooser.getCurrentDirectory().toString() + "//" + fileName);

                        }
                        
                        pw = new PrintWriter(selectedFile);
                        pw.println(text);
                        pw.flush();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        pw.close();
                    }



                }



            }
        });

        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.PAGE_END);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new App();


    }
}

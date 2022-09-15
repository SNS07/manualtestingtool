package com.babyelephantdevelopers;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI implements ActionListener{

    private JFrame frame;
    private  JTextArea errorText;

    public void startApplication() throws Exception{
        //Creating the Frame
        frame = new JFrame("Testing Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Testing Helper", TitledBorder.CENTER, TitledBorder.TOP));
        //panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS));

        errorText = new JTextArea(1,10);
        errorText.setEnabled(false);
        JButton screenshot = new JButton("TakeScreenShot");
       // panel.add(screenshot);
        JPanel locationPanel = new JPanel();
        locationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Select Location", TitledBorder.CENTER, TitledBorder.TOP));
        JButton selectLocation = new JButton("Location");
        locationPanel.add(selectLocation);
        locationPanel.add(errorText);

        JPanel folderPanel = new JPanel();
        folderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Folder Name", TitledBorder.CENTER, TitledBorder.TOP));
        //JLabel label = new JLabel();
        //label.setText("Folder Name");
        JTextArea folderName = new JTextArea(1,10);
        folderName.setSize(2000,20);
        //folderPanel.add(label);
        folderPanel.add(folderName);

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "TestCases", TitledBorder.CENTER, TitledBorder.TOP));
        String[][] rec = {
                { "1","Capture", "This is the description" },
                { "2","Capture", "This is the description"},
                { "3", "Capture","The standard Lorem Ipsum passage, used since the 1500s\n" +
                        "\n" +
                        "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"" }
        };
        String[] header = { "ID", "Button", "Description" };
        final String[] selectedDes = {""};
        JTable table = new JTable(rec, header);
       // table.getColumn("Description").setCellRenderer(new TextRenderer());
        table.getColumn("Button").setCellRenderer(new ButtonRenderer());
        table.getColumn("Button").setCellEditor(
                new ButtonEditor(new JCheckBox()));
       // updateRowHeights(table);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                String selectedCellValue = (String) table.getValueAt(table.getSelectedRow() , 2);
                selectedDes[0] = selectedCellValue;
                System.out.println(selectedCellValue);
                JPanel disPanel = new JPanel();
                disPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), "Description", TitledBorder.CENTER, TitledBorder.TOP));
                disPanel.setLayout(new BorderLayout());
                JTextArea testCaseDis = new JTextArea(10,50);
                testCaseDis.setText(selectedDes[0]);
                disPanel.add(new JScrollPane(testCaseDis));
                panel.add(disPanel);
                testCaseDis.revalidate();
                testCaseDis.repaint();
                //frame.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        tablePanel.add(new JScrollPane(table));

        JPanel disPanel = new JPanel();
        disPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Description", TitledBorder.CENTER, TitledBorder.TOP));
        disPanel.setLayout(new BorderLayout());
        JTextArea testCaseDis = new JTextArea(10,50);
        testCaseDis.setText(selectedDes[0]);
        disPanel.add(new JScrollPane(testCaseDis));

        panel.add(locationPanel);
        panel.add(folderPanel);
        panel.add(tablePanel);
        //panel.add(disPanel);
        //panel.add(fileChooser);
        //frame.setLayout( new BoxLayout(frame, BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel);
        //frame.getContentPane().add( folderPanel);
        frame.setVisible(true);
        screenshot.addActionListener(this);
        selectLocation.addActionListener(this);
    }

    private void updateRowHeights(JTable table)
    {
        for (int row = 0; row < table.getRowCount(); row++)
        {
            int rowHeight = table.getRowHeight();

            for (int column = 0; column < table.getColumnCount(); column++)
            {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table.setRowHeight(row, rowHeight);
        }
    }

    private void takeScreenshot() throws IOException, AWTException {
        frame.setState(Frame.ICONIFIED);
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        errorText.setText(image.toString());
        ImageIO.write(image, "png", new File("screenshot.png"));
        frame.setState(Frame.NORMAL);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        System.out.println(com);
        if("TakeScreenShot".equals(com)){
            try {
                takeScreenshot();
            } catch (IOException ex) {
                errorText.setText(ex.getMessage());
                throw new RuntimeException(ex);
            } catch (AWTException ex) {
                errorText.setText(ex.getMessage());
                throw new RuntimeException(ex);
            }
        }else if ("Location".equals(com)){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int r = fileChooser.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                // set the label to the path of the selected directory
                errorText.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

    }



    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                //
                //
                JOptionPane.showMessageDialog(button, label + ": Ouch!");
                // System.out.println(label + ": Ouch!");
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecondTask extends JFrame {

    private JLabel filenameLabel;
    private JTextField filenameField;
    private JButton loadButton;
    private JTable wordsTable;
    private DefaultTableModel tableModel;

    public SecondTask() {
        super("Words in Lexicographic Order");

        filenameLabel = new JLabel("Enter filename:");
        filenameField = new JTextField();
        loadButton = new JButton("Load");
        wordsTable = new JTable();

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Word"});
        wordsTable.setModel(tableModel);

        loadButton.addActionListener(new LoadButtonListener());

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(filenameLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        topPanel.add(filenameField);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(loadButton);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(wordsTable), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadWordsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> words = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] lineWords = line.toLowerCase().split(" ");
                    Collections.addAll(words, lineWords);
                }
            }

            Collections.sort(words);
            for (String word : words) {
                if (!word.matches(".*\\d.*")) {
                    tableModel.addRow(new Object[]{word});
                }
            }
        } catch (IOException e) {
            showErrorDialog("Error reading file: " + e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filename = filenameField.getText();
            if (!filename.isEmpty()) {
                loadWordsFromFile(filename);
            } else {
                showErrorDialog("Please enter a filename");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SecondTask frame = new SecondTask();
            frame.setVisible(true);
        });
    }
}

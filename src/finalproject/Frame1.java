package finalproject;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Frame1 extends JFrame {

    private JTextField name;
    private JTextField antrian;
    private JButton jButton1;
    private JButton jButton2;

    public Frame1() {
        initComponents();
    }

    public String nama() {
        return "Nama               : " + name.getText();
    }

    public String Antrian() {
        return "No. Antrian      : " + antrian.getText();
    }

    private void initComponents() {
        // Set the layout for the main panel to BorderLayout
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout(10, 10));

        // Header Label
        JLabel headerLabel = new JLabel("Data Pemesan", JLabel.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 153, 153));
        jPanel1.add(headerLabel, BorderLayout.NORTH);

        // Create a JPanel for the form components
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout for fields and buttons

        // Nama Label and TextField
        JLabel namaLabel = new JLabel("Nama               :");
        namaLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        namaLabel.setForeground(new Color(0, 153, 153));
        name = new JTextField(20); // Set width for better visibility
        name.setBackground(new Color(0, 153, 153));
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font for better readability
        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                nameKeyTyped(evt);
            }
        });
        formPanel.add(namaLabel);
        formPanel.add(name);

        // Antrian Label and TextField
        JLabel antrianLabel = new JLabel("No. Antrian       :");
        antrianLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        antrianLabel.setForeground(new Color(0, 153, 153));
        antrian = new JTextField(20); // Set width for better visibility
        antrian.setBackground(new Color(0, 153, 153));
        antrian.setForeground(Color.WHITE);
        antrian.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font for better readability
        antrian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                antrianKeyTyped(evt);
            }
        });
        formPanel.add(antrianLabel);
        formPanel.add(antrian);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        jButton1 = new JButton("Next");
        jButton1.setBackground(new Color(0, 153, 153));
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(evt -> jButton1ActionPerformed());
        jButton2 = new JButton("Cek Data");
        jButton2.setBackground(new Color(0, 153, 153));
        jButton2.setForeground(Color.WHITE);
        jButton2.addActionListener(evt -> jButton2ActionPerformed());
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton2);

        // Add form and button panels to the main panel
        jPanel1.add(formPanel, BorderLayout.CENTER);
        jPanel1.add(buttonPanel, BorderLayout.SOUTH);

        // Nosto Label and Icon Panel
        JPanel nostoPanel = new JPanel();
        JLabel nostoLabel = new JLabel("NOSTO");
        nostoLabel.setFont(new Font("STLiti", Font.PLAIN, 55));
        nostoLabel.setForeground(new Color(0, 153, 153));
        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/finalproject/gambar/icon2.png")));
        nostoPanel.add(nostoLabel);
        nostoPanel.add(iconLabel);

        // Background Label Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/finalproject/gambar/BG (2).jpg"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(jPanel1, BorderLayout.CENTER);  // Add content panel inside background panel

        // Set up the frame layout with background behind content
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(nostoPanel, BorderLayout.NORTH);
        getContentPane().add(backgroundPanel, BorderLayout.CENTER);

        // Basic JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Data Pemesan");
        setUndecorated(true);
        setSize(600, 400);  // Adjust window size to match your desired dimensions
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed() {
        if (name.getText().isEmpty() || antrian.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi data Anda");
        } else {
            // Create and show Frame2, passing the current frame (this) as the argument
            Frame2 frame2 = new Frame2(this);
            frame2.setVisible(true);
            this.setVisible(false);  // Hide Frame1 when showing Frame2
        }
    }

    private void jButton2ActionPerformed() {
        if (name.getText().isEmpty() || antrian.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi data Anda");
        } else {
            JOptionPane.showMessageDialog(null, nama() + "\n" + Antrian(), "Data Pemesan", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void antrianKeyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9') && antrian.getText().length() < 3 || (c == KeyEvent.VK_BACK_SPACE))) {
            getToolkit().beep();
            evt.consume();
        }
    }

    private void nameKeyTyped(KeyEvent evt) {
        char d = evt.getKeyChar();
        if (!Character.isLetter(d)) {
            getToolkit().beep();
            evt.consume();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frame1().setVisible(true));
    }
}

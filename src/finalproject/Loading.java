package finalproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Loading extends javax.swing.JFrame {

    private JLabel persen;
    private JProgressBar progress;
    private Timer loadingTimer;

    public Loading() {
        initComponents();
        setupLoadingTimer();
    }

    private void setupLoadingTimer() {
        loadingTimer = new Timer(50, new ActionListener() {
            int progressValue = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressValue <= 100) {
                    persen.setText(progressValue + "%");
                    progress.setValue(progressValue);
                    progressValue++;

                    if (progressValue > 100) {
                        loadingTimer.stop();
                        openFrame1();  // Open Frame1 after loading completes
                    }
                }
            }
        });
        loadingTimer.start();
    }

    private void openFrame1() {
        SwingUtilities.invokeLater(() -> {
            // Initialize Frame1 and make it visible
            Frame1 frame1 = new Frame1();
            frame1.setVisible(true);
            dispose(); // Close the loading screen after opening Frame1
        });
    }

    private void initComponents() {
        JPanel jPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome To");
        welcomeLabel.setFont(new Font("STZhongsong", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(0, 153, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel1.add(welcomeLabel, gbc);

        // NOSTO Label
        JLabel nostoLabel = new JLabel("NOSTO");
        nostoLabel.setFont(new Font("STLiti", Font.PLAIN, 55));
        nostoLabel.setForeground(new Color(0, 153, 153));
        gbc.gridy = 1;
        jPanel1.add(nostoLabel, gbc);

        // Icon
        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/finalproject/gambar/icon3.png")));
        gbc.gridy = 2;
        jPanel1.add(iconLabel, gbc);

        // Percentage Label
        persen = new JLabel("0%");
        persen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        persen.setForeground(new Color(0, 153, 153));
        gbc.gridy = 3;
        jPanel1.add(persen, gbc);

        // Progress Bar
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(500, 10));
        progress.setForeground(new Color(0, 153, 153));
        gbc.gridy = 4;
        jPanel1.add(progress, gbc);

        getContentPane().add(jPanel1);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Loading loading = new Loading();
            loading.setVisible(true);
        });
    }
}

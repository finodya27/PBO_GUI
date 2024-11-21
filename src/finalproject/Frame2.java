package finalproject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Frame2 extends JFrame {
    private static final Map<String, Integer> PESANAN_PRICES = new HashMap<>() {{
        put("Strawberry", 5000);
        put("Chocolate", 5000);
        put("Vanilla", 5000);
        put("Mix 2 Rasa", 7000);
        put("Mix 3 Rasa", 10000);
    }};

    private static final Map<String, Integer> SNACK_PRICES = new HashMap<>() {{
        put("No Snack", 0);
        put("French Fries", 2000);
        put("Waffle", 3000);
        put("Donut", 4000);
    }};

    private static final Map<String, Integer> WADAH_PRICES = new HashMap<>() {{
        put("Cup", 1000);
        put("Cone", 1500);
    }};

    private Frame1 previousFrame;
    private JComboBox<String> pesanan;
    private JComboBox<String> snack;
    private JComboBox<String> wadah;
    private JTextField jumlahbeli;
    private JTextField jumlahsnack;
    private JLabel hargaPesananLabel;
    private JLabel hargaSnackLabel;
    private JLabel hargaWadahLabel;
    private JLabel totalharga;
    private JButton hitung;
    private JButton reset;
    private JButton pesan;
    private JButton exit;

    public Frame2(Frame1 previousFrame) {
        this.previousFrame = previousFrame;
        initComponents();
    }

    private void initComponents() {
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
    
        // Judul
        JLabel judulLabel = new JLabel("Form Pesanan");
        judulLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        judulLabel.setForeground(new Color(0, 153, 153));
        jPanel1.add(judulLabel);
    
        // Tambahkan label harga di awal
        JPanel hargaInfoPanel = new JPanel(new GridLayout(3, 2));
        hargaInfoPanel.setBorder(BorderFactory.createTitledBorder("Daftar Harga"));
        
        hargaInfoPanel.add(new JLabel("Es Krim:"));
        JLabel pesananPriceLabel = new JLabel(formatPriceList(PESANAN_PRICES));
        hargaInfoPanel.add(pesananPriceLabel);
        
        hargaInfoPanel.add(new JLabel("Snack:"));
        JLabel snackPriceLabel = new JLabel(formatPriceList(SNACK_PRICES));
        hargaInfoPanel.add(snackPriceLabel);
        
        jPanel1.add(hargaInfoPanel);
    
        // Pesanan
        JPanel pesananPanel = createLabeledComboPanel("Pesanan:", 
            pesanan = new JComboBox<>(new String[] {"Pilih...", "Strawberry", "Chocolate", "Vanilla", "Mix 2 Rasa", "Mix 3 Rasa"}));
        hargaPesananLabel = new JLabel("Rp 0");
        pesananPanel.add(hargaPesananLabel);
        pesanan.addActionListener(e -> {
            updateSelectedPrice(pesanan, hargaPesananLabel, PESANAN_PRICES);
            togglePesanButton();  // Check input status and enable/disable Pesan button
        });
        jPanel1.add(pesananPanel);
    
        // Jumlah Pesanan
        JPanel jumlahPanel = createLabeledTextPanel("Jumlah:", 
            jumlahbeli = createNumberTextField());
        jumlahbeli.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                togglePesanButton();  // Enable or disable button when amount is changed
            }
        });
        jPanel1.add(jumlahPanel);
    
        // Snack
        JPanel snackPanel = createLabeledComboPanel("Snack:", 
            snack = new JComboBox<>(new String[] {"Pilih...", "No Snack", "French Fries", "Waffle", "Donut"}));
        hargaSnackLabel = new JLabel("Rp 0");
        snackPanel.add(hargaSnackLabel);
        snack.addActionListener(e -> {
            updateSelectedPrice(snack, hargaSnackLabel, SNACK_PRICES);
            togglePesanButton();
        });
        jPanel1.add(snackPanel);
    
        // Jumlah Snack
        JPanel jumlahSnackPanel = createLabeledTextPanel("Jumlah Snack:", 
            jumlahsnack = createNumberTextField());
        jumlahsnack.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                togglePesanButton();
            }
        });
        jPanel1.add(jumlahSnackPanel);
    
// Pada panel Wadah
JPanel wadahPanel = createLabeledComboPanel("Wadah:", 
    wadah = new JComboBox<>(WADAH_PRICES.keySet().toArray(new String[0])));
hargaWadahLabel = new JLabel("Rp " + (PESANAN_PRICES.containsKey((String) pesanan.getSelectedItem()) ? WADAH_PRICES.get("Cup") : 0));
wadahPanel.add(hargaWadahLabel);
wadah.addActionListener(e -> updateSelectedPrice(wadah, hargaWadahLabel, WADAH_PRICES));

    
        JPanel totalHargaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalHargaPanel.add(new JLabel("Total Harga:"));
        totalharga = new JLabel("Rp 0");
        totalHargaPanel.add(totalharga);
        jPanel1.add(totalHargaPanel);
    
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
    
        hitung = new JButton("Hitung");
        reset = new JButton("Reset");
        pesan = new JButton("Pesan");
        pesan.setEnabled(false);  // Disable Pesan button initially
        exit = new JButton("Exit");
    
        buttonPanel.add(hitung);
        buttonPanel.add(reset);
        buttonPanel.add(pesan);
        buttonPanel.add(exit);
    
        jPanel1.add(buttonPanel);
    
        // Action Listeners
        hitung.addActionListener(e -> total());
        reset.addActionListener(e -> reset());
        pesan.addActionListener(e -> pesanActionPerformed());
        exit.addActionListener(e -> exitActionPerformed());
    
        add(new JScrollPane(jPanel1));
        setTitle("Form Pesanan");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void togglePesanButton() {
        // Cek jika tombol Pesan bisa diaktifkan dengan memastikan setidaknya ada satu item yang valid
        boolean isPesananValid = pesanan.getSelectedIndex() != 0 || !jumlahbeli.getText().isEmpty();
        boolean isSnackValid = snack.getSelectedIndex() != 0 || !jumlahsnack.getText().isEmpty();
        boolean isWadahValid = wadah.getSelectedIndex() != 0;
    
        // Aktifkan tombol Pesan jika setidaknya satu item valid dan total sudah dihitung
        pesan.setEnabled((isPesananValid || isSnackValid || isWadahValid) && !totalharga.getText().equals("Rp 0"));
    }

    private String formatPriceList(Map<String, Integer> priceMap) {
        StringBuilder sb = new StringBuilder("<html>");
        priceMap.forEach((key, value) -> 
            sb.append(key).append(": Rp ").append(value).append("<br/>")
        );
        return sb.append("</html>").toString();
    }

    private void updateSelectedPrice(JComboBox<String> combo, JLabel priceLabel, Map<String, Integer> priceMap) {
        String selected = (String) combo.getSelectedItem();
        Integer price = priceMap.getOrDefault(selected, 0);
        priceLabel.setText("Rp " + price);
        total();
    }

    private JPanel createLabeledComboPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(comboBox);
        return panel;
    }

    private JPanel createLabeledTextPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private JTextField createNumberTextField() {
        JTextField textField = new JTextField(10);
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    evt.consume();
                }
            }
        });
        return textField;
    }

    private void total() {
        try {
            // Ambil jumlah dari text field, jika kosong anggap 0
            int jumlah = jumlahbeli.getText().isEmpty() ? 0 : Integer.parseInt(jumlahbeli.getText());
            int jumlahSnack = jumlahsnack.getText().isEmpty() ? 0 : Integer.parseInt(jumlahsnack.getText());
    
            // Ambil harga pesanan, snack, dan wadah
            int hargaPesanan = PESANAN_PRICES.getOrDefault((String) pesanan.getSelectedItem(), 0);
            int hargaSnack = SNACK_PRICES.getOrDefault((String) snack.getSelectedItem(), 0);
            int hargaWadah = WADAH_PRICES.getOrDefault((String) wadah.getSelectedItem(), 0);
    
            // Jika ada jumlah es krim yang dipilih, jumlah wadah sesuai dengan jumlah es krim
            int jumlahWadah = jumlah > 0 ? jumlah : 1;
    
            // Hitung total harga
            int total = (jumlah * hargaPesanan) + (jumlahSnack * hargaSnack) + (jumlahWadah * hargaWadah);
            totalharga.setText("Rp " + total);
    
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harap masukkan angka yang valid untuk jumlah", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void reset() {
        // Reset pilihan pesanan, snack, dan wadah ke index kosong
        pesanan.setSelectedIndex(-1); // Tidak ada pilihan yang dipilih
        snack.setSelectedIndex(-1);   // Tidak ada pilihan yang dipilih
        wadah.setSelectedIndex(-1);   // Tidak ada pilihan yang dipilih
    
        // Kosongkan jumlah beli dan jumlah snack
        jumlahbeli.setText("");
        jumlahsnack.setText("");
    
        // Set harga label ke "Rp 0"
        hargaPesananLabel.setText("Rp 0");
        hargaSnackLabel.setText("Rp 0");
        hargaWadahLabel.setText("Rp 0");
    
        // Set total harga ke "Rp 0"
        totalharga.setText("Rp 0");
    }

    private void pesanActionPerformed() {
        if (validateInput()) {
            String pesananText = (String) pesanan.getSelectedItem();
            String snackText = (String) snack.getSelectedItem();
            String jumlahBeli = jumlahbeli.getText();
            String jumlahSnack = jumlahsnack.getText();
    
            String message = "Daftar Pesanan:\n" +
                    pesananText + " " + jumlahBeli + "\n" +
                    snackText + " " + jumlahSnack + "\n" +
                    "Total harga: " + totalharga.getText() + "\n\n" +
                    "Silahkan Lanjut ke Pembayaran";
            JOptionPane.showMessageDialog(this, message);
        }
    }
    

    private boolean validateInput() {
        // Memastikan tidak semua pilihan kosong (default)
        if ((pesanan.getSelectedIndex() == 0 && snack.getSelectedIndex() == 0 && wadah.getSelectedIndex() == 0) ||
            jumlahbeli.getText().isEmpty() && jumlahsnack.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi beberapa input, minimal satu pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void exitActionPerformed() {
        if (previousFrame != null) {
            previousFrame.setVisible(true);
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame1 frame1 = new Frame1();
            Frame2 frame2 = new Frame2(frame1);
            frame2.setVisible(true);
        });
    }
}
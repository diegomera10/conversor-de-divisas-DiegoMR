import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CurrencyConverterGUI extends JFrame {
    private HashMap<String, Double> rates = new HashMap<>();
    private JComboBox<String> cbFrom, cbTo;
    private JTextField tfAmount;
    private JLabel lblResult;

    public CurrencyConverterGUI() {
        initRates();
        initUI();
    }

    private void initRates() {
        rates.put("USD", 1.0);
        rates.put("EUR", 0.92);
        rates.put("MXN", 17.50);
        rates.put("GBP", 0.79);
        rates.put("JPY", 157.0);
    }

    private void initUI() {
        setTitle("Conversor de Monedas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 180);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] currencies = rates.keySet().toArray(new String[0]);

        c.gridx = 0; c.gridy = 0;
        add(new JLabel("De:"), c);

        c.gridx = 1; c.gridy = 0;
        cbFrom = new JComboBox<>(currencies);
        add(cbFrom, c);

        c.gridx = 0; c.gridy = 1;
        add(new JLabel("A:"), c);

        c.gridx = 1; c.gridy = 1;
        cbTo = new JComboBox<>(currencies);
        cbTo.setSelectedIndex(1);
        add(cbTo, c);

        c.gridx = 0; c.gridy = 2;
        add(new JLabel("Cantidad:"), c);

        c.gridx = 1; c.gridy = 2;
        tfAmount = new JTextField("1.00", 10);
        add(tfAmount, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        JButton btnConvert = new JButton("Convertir");
        add(btnConvert, c);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        lblResult = new JLabel("Resultado: ");
        lblResult.setFont(lblResult.getFont().deriveFont(Font.BOLD, 14f));
        add(lblResult, c);

        btnConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConvert();
            }
        });
    }

    private void onConvert() {
        try {
            String from = (String) cbFrom.getSelectedItem();
            String to = (String) cbTo.getSelectedItem();
            double amount = Double.parseDouble(tfAmount.getText().trim());
            double res = convert(from, to, amount);
            lblResult.setText(String.format("Resultado: %.4f %s", res, to));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convert(String from, String to, double amount) {
        if (!rates.containsKey(from) || !rates.containsKey(to))
            throw new IllegalArgumentException("Moneda no soportada.");

        double amountInUSD = amount / rates.get(from);
        return amountInUSD * rates.get(to);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverterGUI app = new CurrencyConverterGUI();
            app.setVisible(true);
        });
    }
}

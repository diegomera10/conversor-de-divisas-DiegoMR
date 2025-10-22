import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverterCLI {
    private static final Map<String, Double> rates = new HashMap<>();

    static {
        rates.put("USD", 1.0);
        rates.put("EUR", 0.92);
        rates.put("MXN", 17.50);
        rates.put("GBP", 0.79);
        rates.put("JPY", 157.0);
    }

    public static double convert(String from, String to, double amount) {
        from = from.toUpperCase();
        to = to.toUpperCase();
        if (!rates.containsKey(from) || !rates.containsKey(to)) {
            throw new IllegalArgumentException("Moneda no soportada: " + from + " o " + to);
        }

        double amountInUSD = amount / rates.get(from);
        return amountInUSD * rates.get(to);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Conversor de Monedas (CLI) ===");
        System.out.println("Monedas soportadas: " + rates.keySet());
        try {
            System.out.print("Moneda origen (ej: USD): ");
            String from = sc.next().trim();
            System.out.print("Moneda destino (ej: EUR): ");
            String to = sc.next().trim();
            System.out.print("Cantidad: ");
            double amount = sc.nextDouble();

            double result = convert(from, to, amount);
            System.out.printf("%.4f %s = %.4f %s%n", amount, from.toUpperCase(), result, to.toUpperCase());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}

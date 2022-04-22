package Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CalculatorMain {

    static Scanner scanner = new Scanner(System.in);
    static int employmentPeriod;
    static BigDecimal monthlySalary;
    static BigDecimal monthlyExpenses;
    static BigDecimal monthlyCreditExpenses;
    static BigDecimal sumCreditBalance;

    public static void main(String[] args) {

        System.out.println("Proszę o podanie okresu zatrudnienia (msc): ");
        employmentPeriod = scanner.nextInt();

        System.out.println("Proszę o podanie miesięcznego dochodu w PLN:");
        monthlySalary = scanner.nextBigDecimal();

        System.out.println("Proszę o podanie miesięcznych kosztów utrzymania:");
        monthlyExpenses = scanner.nextBigDecimal();

        System.out.println("Proszę o podanie miesięcznych zobowiązań kredytowych:");
        monthlyCreditExpenses = scanner.nextBigDecimal();

        System.out.println("Proszę o podanie sumy sald kredytów ratalnych");
        sumCreditBalance = scanner.nextBigDecimal();

        if(employmentPeriod < 6 || maksymalnaMiesiecznaRata(monthlySalary,
                monthlyExpenses, monthlyCreditExpenses).doubleValue() < 0 || maksymalnaWartoscKredytu().doubleValue() > 150000 ||
                maksymalnaWartoscKredytu().doubleValue() < 5000) {
            System.out.println("Brak możliwości przedstawienia oferty");

        } else if (employmentPeriod >= 6 && employmentPeriod <= 12) {
            oferta1(employmentPeriod);
        } else if (employmentPeriod > 12 && employmentPeriod <= 36) {
            oferta2(employmentPeriod);
        } else if (employmentPeriod > 36 && employmentPeriod <= 60) {
            oferta3(employmentPeriod);
        } else if (employmentPeriod > 60 && employmentPeriod >= 100) {
            oferta4(employmentPeriod);
        }
    }

    public static int okresKredytowania(int employmentPeriod) {
        return Math.min(employmentPeriod, 100);
    }

    public static BigDecimal maksymalnaMiesiecznaRata(BigDecimal monthlySalary,
                                                      BigDecimal monthlyExpenses, BigDecimal monthlyCreditExpenses) {
        BigDecimal rata = monthlySalary.subtract(monthlyExpenses).subtract(monthlyCreditExpenses);
        List<BigDecimal> dti = new ArrayList<>();
        dti.add(BigDecimal.valueOf(0.60));
        dti.add(BigDecimal.valueOf(0.50));
        dti.add(BigDecimal.valueOf(0.55));
        List<BigDecimal> dti2 = new ArrayList<>();
        dti2.add(dti.get(0).multiply(monthlySalary).subtract(monthlyCreditExpenses));
        dti2.add(dti.get(1).multiply(monthlySalary).subtract(monthlyCreditExpenses));
        dti2.add(dti.get(2).multiply(monthlySalary).subtract(monthlyCreditExpenses));
        dti2.add(rata);
        Collections.sort(dti2);

        BigDecimal bg = new BigDecimal(String.valueOf(dti2.get(0)));
        bg = bg.setScale(2, RoundingMode.CEILING);

        return bg;
    }

    public static BigDecimal maksymalnaWartoscKredytu() {
        BigDecimal maxValue = BigDecimal.valueOf(200000).subtract(sumCreditBalance);
        BigDecimal maxCredit = BigDecimal.valueOf(150000);
        BigDecimal maxCreditRate = maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses).multiply(BigDecimal.valueOf(okresKredytowania(employmentPeriod)));
        List<BigDecimal> maxCreditValue = new ArrayList<>();
        maxCreditValue.add(maxValue);
        maxCreditValue.add(maxCredit);
        maxCreditValue.add(maxCreditRate);
        Collections.sort(maxCreditValue);

        BigDecimal bg2 = new BigDecimal(String.valueOf(maxCreditValue.get(0)));
        bg2 = bg2.setScale(2,RoundingMode.CEILING);

        return bg2;
    }

    public static void oferta1(int x){
        System.out.println("\nOferta nr 1, oprocentowanie 2%\nOkres kredytowania to: 6-" + x);
        System.out.println("Maksymalna miesięczna rata: " + maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses));
        System.out.println("Maksymalna wartość kredytu: " + maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses).multiply(BigDecimal.valueOf(x)));
    }

    public static void oferta2(int x){
        oferta1(12);
        System.out.println("\nOferta nr 2, oprocentowanie 3%\nOkres kredytowania to: 13-"+x);
        System.out.println("Maksymalna miesięczna rata: "+ maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses));
        System.out.println("Maksymalna wartość kredytu: " + maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses).multiply(BigDecimal.valueOf(x)));
    }

    public static void oferta3(int x){
        oferta2(36);
        System.out.println("\nOferta nr 3, oprocentowanie 3%\nOkres kredytowania to: 37-"+x);
        System.out.println("Maksymalna miesięczna rata: "+ maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses));
        System.out.println("Maksymalna wartość kredytu: " + maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses).multiply(BigDecimal.valueOf(x)));
    }

    public static void oferta4(int x){
        oferta3(60);
        System.out.println("\nOferta nr 3, oprocentowanie 3%\nOkres kredytowania to: 60-100");
        System.out.println("Maksymalna miesięczna rata: "+ maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses));
        System.out.println("Maksymalna wartość kredytu: " + maksymalnaMiesiecznaRata(monthlySalary, monthlyExpenses, monthlyCreditExpenses).multiply(BigDecimal.valueOf(100)));
    }
}

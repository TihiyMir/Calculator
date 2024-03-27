import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        try {
            System.out.println("Введите выражение");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            String[] numbers = input.split(" ");
            if (numbers.length != 3) {
                throw new IllegalArgumentException("throws Exception");
            }
            if (input.indexOf('+') == -1 && input.indexOf('-') == -1 && input.indexOf('*') == -1 && input.indexOf('/') == -1) {
                throw new IllegalArgumentException("throws Exception");
            }

            boolean isRoman = isRoman(numbers[0]) && isRoman(numbers[2]);

            int number1 = isRoman ? convertRomanToArabic(numbers[0]) : Integer.parseInt(numbers[0]);
            char operator = numbers[1].charAt(0);
            int number2 = isRoman ? convertRomanToArabic(numbers[2]) : Integer.parseInt(numbers[2]);

            if ((number1 < 1 || number1 > 10) || (number2 < 1 || number2 > 10)) {
                throw new IllegalArgumentException("throws Exception");
            }

            int result = 0;
            switch (operator) {
                case '+':
                    result = number1 + number2;
                    break;
                case '-':
                    result = number1 - number2;
                    if (!isRoman && result < 0) {
                        throw new IllegalArgumentException("throws Exception");
                    }
                    break;
                case '*':
                    result = number1 * number2;
                    break;
                case '/':
                    result = number1 / number2;
                    break;
                default:
                    throw new IllegalArgumentException("throws Exception");
            }

            System.out.println("Результат: " + (isRoman ? (result <= 0 ? "throws Exception" : convertArabicToRoman(result)) : result));

        }  catch (ArithmeticException | IllegalArgumentException e) {
            System.out.println("throws Exception");
        }
    }

    private static boolean isRoman(String input) {
        String romanSymbols = "IVXLCDM";
        for (char c : input.toCharArray()) {
            if (romanSymbols.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    private static int convertRomanToArabic(String romanNumber) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        int result = 0;
        int prevValue = 0;
        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(romanNumber.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }
        return result;
    }

    private static String convertArabicToRoman(int number) {
        if (number < 1 || number > 3999)
            return "throws Exception";
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number / 1000] + hundreds[(number % 1000) / 100]
                + tens[(number % 100) / 10] + units[number % 10];
    }
}

package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\"Напишите выражение: \" = " + "Напишите выражение: ");
        String input = scanner.nextLine();

        System.out.println(calc(input));

    }
    public static String intToRoman(int num) {
        if (num <= 0 || num > 100) {
            throw new IllegalArgumentException("Input is out of range (1 to 100)");
        }

        StringBuilder roman = new StringBuilder();

        String[] symbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        for (int i = symbols.length - 1; i >= 0; i--) {
            while (num >= values[i]) {
                roman.append(symbols[i]);
                num -= values[i];
            }
        }

        return roman.toString();
    }

    public static int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int curValue = getValue(s.charAt(i));//VII, IX
            if (curValue < prevValue) {//1, 1, 5..., 10, 1
                result -= curValue;//9
            } else {
                result += curValue;//1+1+5... , 10,
            }
            prevValue = curValue;//1, 2, 7... , 10,
        }

        return result;
    }

    private static int getValue(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Invalid character: " + romanChar);
        }
    }

    public static String calc(String input) {
        String[] arr = input.split(" ");

        int operand1, operand2, n = 0;
        char operator;

        if (arr.length != 3) {
            throw new IllegalArgumentException("Неверное количество операндов и операторов");
        }


        if (containsRomanNumber(arr[0]) && !containsRomanNumber(arr[2]) || containsRomanNumber(arr[2]) && !containsRomanNumber(arr[0])) {
            throw new IllegalArgumentException("Оба операнды должны быть одного типа");
        } else if (containsRomanNumber(arr[0]) && containsRomanNumber(arr[2])) {
            n = 1;
            operand1 = romanToInt(arr[0]);
            operand2 = romanToInt(arr[2]);
        } else {
            operand1 = Integer.parseInt(arr[0]);
            operand2 = Integer.parseInt(arr[2]);
        }

//        if (containsRomanNumber(arr[2])) {
//            operand2 = romanToInt(arr[2]);
//        } else {
//            operand2 = Integer.parseInt(arr[2]);
//        }
        operator = arr[1].charAt(0);

        if ((operand1 < 1 || operand1 > 10) || (operand2 < 1 || operand2 > 10)) {
            throw new IllegalArgumentException("ограничения на ввод > 10");
        }
        int result;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        }
        if (n == 1) {
            return intToRoman(result);
        }
        return String.valueOf(result);
    }

    public static boolean containsRomanNumber(String input) {
        Pattern pattern = Pattern.compile("[IVXLCDM]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
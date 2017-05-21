package ru.solvers.eq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by onotole on 21/05/2017.
 */
public class LettersEqSolver {
    private ExpressionParser parser = new SpelExpressionParser();
    private Pattern pattern = Pattern.compile("[A-Z]");
    private Random random = new Random();

    public String solver(String eq) {
        int left;
        int right;
        ReplacedData data;
        int attemptCounter = 0;
        do {
            attemptCounter++;
            data = replaceLettersWithDigits(eq);
            String[] resolvedEq = data.getEq().split("=");
            left = parser.parseExpression(resolvedEq[0]).getValue(Integer.class);
            right = parser.parseExpression(resolvedEq[1]).getValue(Integer.class);
        } while (left != right);
        System.out.println("attempts: " + attemptCounter);
        return data.getReplaceData();
    }

    private ReplacedData replaceLettersWithDigits(String eq) {
        StringBuilder sb = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        String letter = getFirstLetter(eq);

        while(! letter.isEmpty()) {
            int digit = numbers.remove(random.nextInt(numbers.size()));
            sb.append(letter).append("=").append(digit).append(", ");
            eq = eq.replaceAll(letter, "" + digit);
            letter = getFirstLetter(eq);
        }

        return new ReplacedData(eq, sb.toString());
    }

    private String getFirstLetter(String word) {
        Matcher m = pattern.matcher(word);
        if (m.find()) {
            return m.group();
        } else {
            return "";
        }
    }

    @AllArgsConstructor
    @Getter
    class ReplacedData {
        String eq;
        String replaceData;
    }

    public static void main(String[] args) {
        LettersEqSolver s = new LettersEqSolver();
        System.out.println(s.solver("ALFA+BETA+GAMA-DELTA=0"));

    }
}

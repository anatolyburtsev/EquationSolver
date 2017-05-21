package ru.solvers.eq;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by onotole on 21/05/2017.
 */
public class ClassicEqSolver {
    private ExpressionParser parser = new SpelExpressionParser();
    private static final int MAX = 1_000_001;
    private static final String VAR = "x";

    public int solve(String eq) {
        String currentExpr;
        for (int i = 0; i < MAX; i++) {
            currentExpr = replaceVar(eq, VAR, i);
            if (isExpRight(currentExpr)) return i;
            currentExpr = replaceVar(eq, VAR, -i);
            if (isExpRight(currentExpr)) return -i;
        }
        throw new RuntimeException("Answer not found!");
    }

    private boolean isExpRight(String expession) {
        String[] parts = expession.split("=");
        long left = parser.parseExpression(parts[0]).getValue(Long.class);
        long right = parser.parseExpression(parts[1]).getValue(Long.class);
        return left == right;
    }

    private String replaceVar(String eq, String var, int digit) {
        return eq.replaceAll(var, "" + digit);
    }



    public static void main(String[] args) {
        String eq = "1*x^2-3*x+2=0"; // answer = 1
//        String eq = "1*x^10-5*10^12*x^8+10^25*x^6-10^37*x^4+5*10^48*x^2-10^60=0"; // answer = 1_000_000
        ClassicEqSolver s = new ClassicEqSolver();
        System.out.println(s.solve(eq));
    }
}

import java.io.*;
import java.util.*;

// The factorization still has a problem.

class LinearEquationSimplifier {

    static char variableForConstant = '$';

    public static void main(String[] args) throws IOException {

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an equation using LETTERS as variables and {+,-} as operators connecting them.\n");
        System.out.println("Example : 0.5x + 3y = 2.5x +3z + 2y -56 + 54-2   =>  + 4.0 - 2.0x + 1.0y - 3.0z = 0\n");
        String equation = new String(consoleInput.readLine());
        equation = equation.replaceAll("\\s+", "");

        equation = constantToVariable(equation);
        String simplifiedEquation = variableToConstant(homogeneousSimplify(equation));

        if(!simplifiedEquation.equals("")) System.out.println("\nSimplified Equation : " + simplifiedEquation + "\n");
    }

    private static String homogeneousSimplify(String equation) {

        String lhs = equation.substring(0, equation.indexOf('='));
        String rhs = equation.substring(equation.indexOf('=') + 1);
        if(rhs == null || lhs == null) {
            System.out.println("Invalid input.");
            return "";
        }

        HashMap<Character, Double> lhsMap = getMap(lhs);
        HashMap<Character, Double> rhsMap = getMap(rhs);
        HashMap<Character, Double> hMap = lhsMap;

        for(Map.Entry<Character, Double> entry : rhsMap.entrySet()) {
            char variable = entry.getKey();
            double coefficient = entry.getValue();
            if(hMap.get(variable) == null) {
                hMap.put(variable, -1 * coefficient);
            } else {
                hMap.put(variable, hMap.get(variable) - coefficient);
            }
        }
        String simplifiedEquation = "";
        for(Map.Entry<Character, Double> entry : hMap.entrySet()) {
            char variable = entry.getKey();
            double coefficient = entry.getValue();
            if(coefficient>0) {
                simplifiedEquation += " + " + coefficient + variable;
            } else if(coefficient < 0) {
                simplifiedEquation += " - " + (-1*coefficient) + variable;
            }
        }
        if(simplifiedEquation!="")
            simplifiedEquation += " = 0";
        return simplifiedEquation;
    }

    private static HashMap<Character, Double> getMap(String s) {

        HashMap<Character, Double> hMap = new HashMap<Character, Double>();
        char currentChar;
        byte sign = 1;
        double coefficient = 0;
        String coefficientString = "0";
        boolean hasCoefficient = false;
        for(int i=0;i<s.length();i++) {
            currentChar = s.charAt(i);

            if(Character.isUpperCase(currentChar) || Character.isLowerCase(currentChar) || currentChar == variableForConstant) {
                if(!hasCoefficient) {
                    coefficientString = String.valueOf(sign);
                }
                coefficient = Double.parseDouble(coefficientString);
                if(hMap.get(currentChar)==null) {
                    hMap.put(currentChar, sign*coefficient);
                } else {
                    hMap.put(currentChar, hMap.get(currentChar) + sign*coefficient);
                }
                coefficient = 0;
                coefficientString = "0";
                sign = 1;
                hasCoefficient = false;
            }

            if(Character.isDigit(currentChar) || currentChar == '.') {
                coefficientString = coefficientString + currentChar;
                hasCoefficient = true;
            }

            if(currentChar == '-') {
                sign = -1;
            }
        }

        return hMap;
    }

    private static String constantToVariable(String equation) {
        String term = "";
        String newEquation = "";
        for(int i=0; i < equation.length(); i++) {
            if(equation.charAt(i) == '+' || equation.charAt(i) == '=') {
                char lastChar = term.charAt(term.length()-1);
                newEquation += term;
                if(lastChar>='0' && lastChar<='9') {
                    newEquation += variableForConstant;
                }
                term = String.valueOf(equation.charAt(i));
                continue;
            } else if(equation.charAt(i) == '-') {
                char lastChar = term.charAt(term.length()-1);
                newEquation += term;
                if(lastChar>='0' && lastChar<='9') {
                    newEquation += variableForConstant;
                }
                term = "-";
                continue;
            } else {
                term += equation.charAt(i);
            }
        }

        char lastChar = term.charAt(term.length()-1);
        newEquation += term;
        if(lastChar>='0' && lastChar<='9') {
            newEquation += variableForConstant;
        }

        return newEquation;
    }

    private static String variableToConstant(String equation) {
        String term = "";
        String newEquation = "";
        for(int i=0; i < equation.length(); i++) {
            if(equation.charAt(i)!=variableForConstant) {
                newEquation += equation.charAt(i);
            }
        }
        return newEquation;
    }
}

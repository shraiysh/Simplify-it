import java.util.*;

// The factorization still has a problem.

class LinearEquationSimplifierResource {

    private static char variableForConstant = '$';

    public static String simplify(String equation) {

        equation = equation.replaceAll("\\s+", "");

        equation = constantToVariable(equation);
        String simplifiedEquation = variableToConstant(homogeneousSimplify(equation, false));

        if(!simplifiedEquation.equals("")) return simplifiedEquation;
        return null;
    }

    public static String simplify(String equation, boolean oneUnitCoefficient) {

        equation = equation.replaceAll("\\s+", "");

        equation = constantToVariable(equation);
        String simplifiedEquation = variableToConstant(homogeneousSimplify(equation, oneUnitCoefficient));

        if(!simplifiedEquation.equals("")) return simplifiedEquation;
        return null;
    }

    private static String homogeneousSimplify(String equation, boolean oneUnitCoefficient) {

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
        if(oneUnitCoefficient)
            hMap = oneUnitCoefficient(hMap);

        String simplifiedEquation = "";
        for(Map.Entry<Character, Double> entry : hMap.entrySet()) {
            char variable = entry.getKey();
            double coefficient = entry.getValue();
            if(coefficient>0) {
                simplifiedEquation += "+" + coefficient + variable;
            } else if(coefficient < 0) {
                simplifiedEquation += "-" + (-1*coefficient) + variable;
            }
        }
        if(simplifiedEquation!="")
            simplifiedEquation += "=0";
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

    private static HashMap<Character, Double> oneUnitCoefficient(HashMap<Character, Double> hMap) {
        boolean foundFactor = false;
        double factor = 1;
        for(Map.Entry<Character, Double> entry : hMap.entrySet()) {
            if(!foundFactor)  {
                factor = entry.getValue();
                foundFactor = true;
            }
            hMap.put(entry.getKey(), entry.getValue()/factor);
        }
        return hMap;
    }
}

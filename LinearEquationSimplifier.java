import java.io.*;

public class LinearEquationSimplifier {
    public static void main(String[] args) throws IOException {

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an equation using LETTERS as variables and {+,-} as operators connecting them.\n");
        System.out.println("Example : 0.5x + 3y = 2.5x +3z + 2y -56 + 54-2   =>  + 4.0 - 2.0x + 1.0y - 3.0z = 0\n");
        String equation = new String(consoleInput.readLine());

        String newEquation = LinearEquationSimplifierResource.simplify(equation);

        System.out.println("\nSimplified Equation : " + newEquation);
    }
}

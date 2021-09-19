import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Expretor Calculassion 2.0\nIt can parse floating-point numbers, operations +, -, *, /, parentheses and one-letter variables.\nExample: (5.1 + 1) * 2 - x * y / 5.31");
        Parser parser = ParserImpl.INSTANCE;
        ExpressionVisitor to_string_visitor = ExpressionToStringVisitor.INSTANCE;
        ExpressionVisitor debug_representation_visitor = ExpressionDebugRepresentationVisitor.INSTANCE;
        Scanner scanner  = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter expression: ");
                Expression expr = parser.parseString(scanner.nextLine());
                System.out.println("You've entered: " + expr.accept(to_string_visitor));
                System.out.println("Tree: " + expr.accept(debug_representation_visitor));
                ExpressionVisitor variable_input_visitor = new VariableInputVisitor();
                Map<Character, Double> map = (Map<Character, Double>)expr.accept(variable_input_visitor);
                System.out.println("Evaluation result: " + expr.accept(new CalculatorVisitor(map)) + "\n");
            } catch (Exception exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }
}

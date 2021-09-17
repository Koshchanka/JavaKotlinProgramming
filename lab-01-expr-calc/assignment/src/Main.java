import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ParserImpl();
        ExpressionVisitor to_string_visitor = new ExpressionToStringVisitor();
        Scanner scanner  = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter expression: ");
                Expression expr = parser.parseString(scanner.nextLine());
                System.out.println("You've entered: " + expr.accept(to_string_visitor));
                ExpressionVisitor variable_input_visitor = new VariableInputVisitor();
                Map<Character, Double> map = (Map<Character, Double>)expr.accept(variable_input_visitor);
                System.out.println("Evaluation result: " + expr.accept(new CalculatorVisitor(map)) + "\n");
            } catch (Exception exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }
}

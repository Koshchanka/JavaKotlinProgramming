import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Expretor Calculassion 2.0\nIt can parse floating-point numbers, operations +, -, *, /, parentheses and one-letter variables.\nExample: (5.1 + 1) * 2 - x * y / 5.31\n");
        Parser parser = ParserImpl.INSTANCE;
        ExpressionVisitor toStringVisitor = ToStringVisitor.INSTANCE;
        ExpressionVisitor debugRepresentationVisitor = DebugRepresentationVisitor.INSTANCE;
        ExpressionVisitor treeDepthVisitor = TreeDepthVisitor.INSTANCE;
        Scanner scanner  = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter expression: ");
                Expression expr = parser.parseString(scanner.nextLine());
                System.out.println("You've entered: " + expr.accept(toStringVisitor));
                System.out.println("Tree: " + expr.accept(debugRepresentationVisitor));
                System.out.println("Tree depth: " + expr.accept(treeDepthVisitor));
                ExpressionVisitor variableInputVisitor = new VariableInputVisitor();
                Map<Character, Double> map = (Map<Character, Double>)expr.accept(variableInputVisitor);
                System.out.println("Evaluation result: " + expr.accept(new CalculatorVisitor(map)) + "\n");
            } catch (Exception exception) {
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }
}

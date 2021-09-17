import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ParserImpl();
        ExpressionVisitor to_string_visitor = new ExpressionToStringVisitor();
        String expression = "7 - 3 * 5 + (y + 2 + (5 + x)) + 3";
        Expression expr = parser.parseString(expression);
        System.out.println("Expression is: " + expr.accept(to_string_visitor));
        ExpressionVisitor input_visitor = new VariableInputVisitor();
        Map<Character, Double> map = (Map<Character, Double>) expr.accept(input_visitor);
        ExpressionVisitor calculator = new CalculatorVisitor(map);
        System.out.print("Expression value is: " + expr.accept(calculator));
    }
}

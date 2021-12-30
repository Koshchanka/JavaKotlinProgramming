import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VariableInputVisitor implements ExpressionVisitor {
    VariableInputVisitor() {
        scanner = new Scanner(System.in);
        values = new HashMap<>();
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        return values;
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return values;
    }

    @Override
    public Object visitVariable(Variable expression) {
        if (!values.containsKey(expression.getSymbol())) {
            System.out.print("Enter value for [" + expression.getSymbol() + "]: ");
            values.put(expression.getSymbol(), scanner.nextDouble());
        }
        return values;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpression().accept(this);
    }

    private final Map<Character, Double> values;
    private final Scanner scanner;
}

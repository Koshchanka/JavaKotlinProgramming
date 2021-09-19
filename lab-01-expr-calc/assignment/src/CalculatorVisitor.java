import java.util.Map;

public class CalculatorVisitor implements ExpressionVisitor {
    private final Map<Character, Double> mValues;

    CalculatorVisitor(Map<Character, Double> values) {
        mValues = values;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        return switch (expression.getOpKind()) {
            case ADD -> (Double) expression.getLeft().accept(this) + (Double) expression.getRight().accept(this);
            case SUB -> (Double) expression.getLeft().accept(this) - (Double) expression.getRight().accept(this);
            case MUL -> (Double) expression.getLeft().accept(this) * (Double) expression.getRight().accept(this);
            case DIV -> (Double) expression.getLeft().accept(this) / (Double) expression.getRight().accept(this);
        };
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return expression.getValue();
    }

    @Override
    public Object visitVariable(Variable expression) {
        return mValues.get(expression.getSymbol());
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpression().accept(this);
    }
}

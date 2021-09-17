import java.util.Map;

public class CalculatorVisitor implements ExpressionVisitor {
    CalculatorVisitor(Map<Character, Double> values) {
        mValues = values;
    }

    private Map<Character, Double> mValues;

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        switch (expression.getOpKind()) {
            case ADD -> {
                return (Double) expression.getLeft().accept(this) + (Double) expression.getRight().accept(this);
            }
            case SUB -> {
                return (Double) expression.getLeft().accept(this) - (Double) expression.getRight().accept(this);
            }
            case MUL -> {
                return (Double) expression.getLeft().accept(this) * (Double) expression.getRight().accept(this);
            }
            case DIV -> {
                return (Double) expression.getLeft().accept(this) / (Double) expression.getRight().accept(this);
            }
        }
        return 0.0;
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

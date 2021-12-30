import java.util.Map;

public class CalculatorVisitor implements ExpressionVisitor {
    private final Map<Character, Double> mValues;

    CalculatorVisitor(Map<Character, Double> values) {
        mValues = values;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        Double ret_val;
        switch (expression.getOpKind()) {
            case ADD:
                ret_val = (Double) expression.getLeft().accept(this) + (Double) expression.getRight().accept(this);
                break;
            case SUB:
                ret_val = (Double) expression.getLeft().accept(this) - (Double) expression.getRight().accept(this);
                break;
            case MUL:
                ret_val = (Double) expression.getLeft().accept(this) * (Double) expression.getRight().accept(this);
                break;
            case DIV:
                ret_val = (Double) expression.getLeft().accept(this) / (Double) expression.getRight().accept(this);
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (ret_val.isNaN() || ret_val.isInfinite()) {
            throw new RuntimeException("Calculator visitor: Invalid result");
        }
        return ret_val;
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

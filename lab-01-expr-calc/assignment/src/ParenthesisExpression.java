public class ParenthesisExpression implements Expression {
    private final Expression mExpression;

    ParenthesisExpression(Expression expression) {
        mExpression = expression;
    }

    Expression getExpression() {
        return mExpression;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
}

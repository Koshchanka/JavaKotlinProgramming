public class ParenthesisExpression implements Expression {
    ParenthesisExpression(Expression expression) {
        mExpression = expression;
    }

    Expression getExpression() {
        return mExpression;
    }

    private Expression mExpression;

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
}

public class BinaryExpression implements Expression {
    BinaryExpression(BinOpKind op_kind) {
        mOpKind = op_kind;
    }

    public Expression getLeft() {
        return mLeft;
    }

    public Expression getRight() {
        return mRight;
    }

    public BinOpKind getOpKind() {
        return mOpKind;
    }

    public void setLeftRight(Expression left, Expression right) {
        mLeft = left;
        mRight = right;
    }

    private Expression mLeft;
    private Expression mRight;
    private BinOpKind mOpKind;

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
}

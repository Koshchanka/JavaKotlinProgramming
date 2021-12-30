public class BinaryExpression implements Expression {
    private Expression mLeft;
    private Expression mRight;
    private final BinOpKind mOpKind;

    BinaryExpression(BinOpKind opKind) {
        mOpKind = opKind;
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

    public void setLeft(Expression left) {
        mLeft = left;
    }

    public void setRight(Expression right) {
        mRight = right;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
}

public class Literal implements Expression {
    Literal(double value) {
        mValue = value;
    }

    public double getValue() {
        return mValue;
    }

    private final double mValue;

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
}

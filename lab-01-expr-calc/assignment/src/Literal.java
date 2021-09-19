public class Literal implements Expression {
    private final double mValue;

    Literal(double value) {
        mValue = value;
    }

    public double getValue() {
        return mValue;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
}

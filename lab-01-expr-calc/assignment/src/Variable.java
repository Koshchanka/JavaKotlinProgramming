public class Variable implements Expression {
    Variable(char symbol) {
        mSymbol = symbol;
    }

    char getSymbol() {
        return mSymbol;
    }

    private final char mSymbol;

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }
}

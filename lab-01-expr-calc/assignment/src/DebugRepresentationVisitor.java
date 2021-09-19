public class DebugRepresentationVisitor implements ExpressionVisitor {
    public static DebugRepresentationVisitor INSTANCE = new DebugRepresentationVisitor();

    private DebugRepresentationVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        return expression.getOpKind().toString() + "(" +
                expression.getLeft().accept(this) + ", " + expression.getRight().accept(this) + ")";
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return String.valueOf(expression.getValue());
    }

    @Override
    public Object visitVariable(Variable expression) {
        return "[" + expression.getSymbol() + "]";
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpression().accept(this);
    }
}

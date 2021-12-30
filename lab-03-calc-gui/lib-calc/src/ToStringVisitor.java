public class ToStringVisitor implements ExpressionVisitor {
    private ToStringVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        String mid;
        switch (expression.getOpKind()) {
            case ADD:
                mid = " + ";
                break;
            case SUB:
                mid = " - ";
                break;
            case MUL:
                mid = " * ";
                break;
            case DIV:
                mid = " / ";
                break;
            default:
                throw new IllegalArgumentException();
        }
        return expression.getLeft().accept(this) + mid + expression.getRight().accept(this);
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return String.valueOf(expression.getValue());
    }

    @Override
    public Object visitVariable(Variable expression) {
        return String.valueOf(expression.getSymbol());
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return "(" + expression.getExpression().accept(this) + ")";
    }

    public static ToStringVisitor INSTANCE = new ToStringVisitor();
}

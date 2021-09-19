public class ExpressionToStringVisitor implements ExpressionVisitor {
    private ExpressionToStringVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        return expression.getLeft().accept(this) +
                switch (expression.getOpKind()) {
                    case ADD -> " + ";
                    case SUB -> " - ";
                    case MUL -> " * ";
                    case DIV -> " / ";
                } +
                expression.getRight().accept(this);
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

    public static ExpressionToStringVisitor INSTANCE = new ExpressionToStringVisitor();
}

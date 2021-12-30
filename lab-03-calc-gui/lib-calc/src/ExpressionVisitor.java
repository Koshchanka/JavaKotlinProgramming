public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expression);
    Object visitLiteral(Literal expression);
    Object visitVariable(Variable expression);
    Object visitParenthesis(ParenthesisExpression expression);
}

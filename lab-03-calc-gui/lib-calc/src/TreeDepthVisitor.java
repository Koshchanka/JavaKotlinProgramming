import com.sun.source.tree.Tree;

import static java.lang.Math.max;

public class TreeDepthVisitor implements ExpressionVisitor {
    private TreeDepthVisitor() {}

    public static TreeDepthVisitor INSTANCE = new TreeDepthVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        return max((Integer) expression.getLeft().accept(this), (Integer) expression.getRight().accept(this)) + 1;
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return 1;
    }

    @Override
    public Object visitVariable(Variable expression) {
        return 1;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return (Integer) expression.getExpression().accept(this) + 1;
    }
}

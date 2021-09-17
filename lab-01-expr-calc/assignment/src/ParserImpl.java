import java.util.ArrayList;

class Int {
    Int(int value_) {
        value = value_;
    }

    public int value;
}

public class ParserImpl implements Parser {
    private boolean isNumberChar(char ch) {
        return '0' <= ch && ch <= '9' || ch == '.';
    }

    private boolean isLetter(char ch) {
        return 'A' <= ch && ch <= 'Z' || 'a' <= ch && ch <= 'z';
    }

    private ArrayList<Expression> tokenize(String input, Int start, boolean parentheses_should_be_imbalanced) {
        ArrayList<Expression> tokens = new ArrayList<>();
        for (; start.value < input.length(); ++start.value) {
            char curr_symbol = input.charAt(start.value);
            if (curr_symbol == '(') {
                ++start.value;
                tokens.add(new ParenthesisExpression(parseString(input, start, true)));
                continue;
            }
            if (curr_symbol == ')') {
                if (!parentheses_should_be_imbalanced) {
                    throw new RuntimeException("Wrong parentheses placement");
                } else {
                    return tokens;
                }
            }
            if (isNumberChar(curr_symbol)) {
                StringBuilder number_str = new StringBuilder();
                while (start.value < input.length() && isNumberChar(curr_symbol = input.charAt(start.value++))) {
                    number_str.append(curr_symbol);
                }
                tokens.add(new Literal(Double.parseDouble(number_str.toString())));
                if (start.value != input.length()) {
                    start.value -= 2;
                }
                continue;
            }
            if (isLetter(curr_symbol)) {
                tokens.add(new Variable(curr_symbol));
                continue;
            }
            switch (curr_symbol) {
                case '+' -> tokens.add(new BinaryExpression(BinOpKind.ADD));
                case '-' -> tokens.add(new BinaryExpression(BinOpKind.SUB));
                case '*' -> tokens.add(new BinaryExpression(BinOpKind.MUL));
                case '/' -> tokens.add(new BinaryExpression(BinOpKind.DIV));
                case ' ' -> {
                }
                default -> throw new RuntimeException("Invalid char in input");
            }
        }
        if (parentheses_should_be_imbalanced) {
            throw new RuntimeException("Wrong parentheses placement");
        }
        return tokens;
    }

    @Override
    public Expression parseString(String input) {
        return parseString(input, new Int(0), false);
    }

    private void clampTokens(ArrayList<Expression> tokens, BinOpKind first, BinOpKind second) {
        int iter = 0;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i) instanceof BinaryExpression) {
                BinOpKind op_kind = ((BinaryExpression) tokens.get(i)).getOpKind();
                if (op_kind == first || op_kind == second) {
                    ((BinaryExpression) tokens.get(i)).setLeftRight(tokens.get(iter - 1), tokens.get(i + 1));
                    tokens.set(iter - 1, tokens.get(i++));
                    continue;
                }
            }
            tokens.set(iter++, tokens.get(i));
        }
        tokens.subList(iter, tokens.size()).clear();
    }

    private Expression parseString(String input, Int start, boolean parentheses_should_be_imbalanced) {
        ArrayList<Expression> tokens = tokenize(input, start, parentheses_should_be_imbalanced);
        checkTokenSequenceValidity(tokens);
        clampTokens(tokens, BinOpKind.MUL, BinOpKind.DIV);
        clampTokens(tokens, BinOpKind.ADD, BinOpKind.SUB);
        return tokens.get(0);
    }

    private void checkTokenSequenceValidity(ArrayList<Expression> tokens) {
        if (tokens.size() % 2 != 1) {
            throw new RuntimeException("Invalid expression");
        }
        for (int i = 0; i < tokens.size(); ++i) {
            if ((tokens.get(i) instanceof BinaryExpression) != (i % 2 == 1)) {
                throw new RuntimeException("Invalid expression");
            }
        }
    }
}

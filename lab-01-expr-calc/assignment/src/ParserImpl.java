import java.util.ArrayList;

public class ParserImpl implements Parser {
    private ParserImpl() {}

    public static Parser INSTANCE = new ParserImpl();

    @Override
    public Expression parseString(String input) {
        return parseString(input, new Tokenizer(), false);
    }

    private void clampTokens(ArrayList<Expression> tokens, BinOpKind first, BinOpKind second) {
        int iter = 0;
        for (int i = 0; i < tokens.size(); ++i) {
            if (tokens.get(i) instanceof BinaryExpression) {
                BinOpKind op_kind = ((BinaryExpression) tokens.get(i)).getOpKind();
                if (op_kind == first || op_kind == second) {
                    ((BinaryExpression) tokens.get(i)).setLeft(tokens.get(iter - 1));
                    ((BinaryExpression) tokens.get(i)).setRight(tokens.get(i + 1));
                    tokens.set(iter - 1, tokens.get(i++));
                    continue;
                }
            }
            tokens.set(iter++, tokens.get(i));
        }
        tokens.subList(iter, tokens.size()).clear();
    }

    private Expression parseString(String input, Tokenizer tokenizer, boolean shouldEncounterEnclosingParenthesis) {
        ArrayList<Expression> tokens = tokenizer.tokenize(input, shouldEncounterEnclosingParenthesis);
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

    private class Tokenizer {

        Tokenizer() {
            pos = 0;
        }

        private boolean isNumberChar(char ch) {
            return '0' <= ch && ch <= '9' || ch == '.';
        }

        private boolean isLetter(char ch) {
            return 'A' <= ch && ch <= 'Z' || 'a' <= ch && ch <= 'z';
        }

        private ArrayList<Expression> tokenize(String input, boolean shouldEncounterClosingParenthesis) {
            ArrayList<Expression> tokens = new ArrayList<>();
            for (; pos < input.length(); ++pos) {
                char curr_symbol = input.charAt(pos);
                if (curr_symbol == '(') {
                    ++pos;
                    tokens.add(new ParenthesisExpression(parseString(input, this, true)));
                    continue;
                }
                if (curr_symbol == ')') {
                    if (!shouldEncounterClosingParenthesis) {
                        throw new RuntimeException("Wrong parentheses placement");
                    } else {
                        return tokens;
                    }
                }
                if (isNumberChar(curr_symbol)) {
                    StringBuilder numberStr = new StringBuilder();
                    boolean good = false;
                    while (pos < input.length() && (good = true) && isNumberChar(curr_symbol = input.charAt(pos++))) {
                        numberStr.append(curr_symbol);
                        good = false;
                    }
                    tokens.add(new Literal(Double.parseDouble(numberStr.toString())));
                    if (!good) {
                        break;
                    }
                    pos -= 2;
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
            if (shouldEncounterClosingParenthesis) {
                throw new RuntimeException("Wrong parentheses placement");
            }
            return tokens;
        }
        private int pos;

    }
}

import javax.swing.*
import java.awt.EventQueue
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets


class MainWindow : JFrame() {
    private val result = JLabel()
    private val input = JTextField()
    private val bindings: MutableMap<Char, Double> = HashMap()
    private val bindingsList = DefaultListModel<String>()

    init {
        title = "yandex.browser"
        defineLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        setSize(300, 300)
        input.horizontalAlignment = SwingConstants.RIGHT
    }

    private fun defineLayout() {
        val gbl = GridBagLayout()
        val gbc = GridBagConstraints()
        contentPane.layout = gbl
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.insets = Insets(1, 1, 1, 1)

        gbc.gridx = 0
        gbc.gridy = 0
        gbc.gridwidth = 3
        contentPane.add(input, gbc)
        gbc.gridwidth = 1

        gbc.gridx = 3
        gbc.gridy = 0
        contentPane.add(result, gbc)

        val button0 = JButton("0")
        button0.addActionListener { typeChar('0') }
        gbc.gridx = 1
        gbc.gridy = 5
        contentPane.add(button0, gbc)

        val button1 = JButton("1")
        button1.addActionListener { typeChar('1') }
        gbc.gridx = 0
        gbc.gridy = 2
        contentPane.add(button1, gbc)

        val button2 = JButton("2")
        button2.addActionListener { typeChar('2') }
        gbc.gridx = 1
        gbc.gridy = 2
        contentPane.add(button2, gbc)

        val button3 = JButton("3")
        button3.addActionListener { typeChar('3') }
        gbc.gridx = 2
        gbc.gridy = 2
        contentPane.add(button3, gbc)

        val button4 = JButton("4")
        button4.addActionListener { typeChar('4') }
        gbc.gridx = 0
        gbc.gridy = 3
        contentPane.add(button4, gbc)

        val button5 = JButton("5")
        button5.addActionListener { typeChar('5') }
        gbc.gridx = 1
        gbc.gridy = 3
        contentPane.add(button5, gbc)

        val button6 = JButton("6")
        button6.addActionListener { typeChar('6') }
        gbc.gridx = 2
        gbc.gridy = 3
        contentPane.add(button6, gbc)

        val button7 = JButton("7")
        button7.addActionListener { typeChar('7') }
        gbc.gridx = 0
        gbc.gridy = 4
        contentPane.add(button7, gbc)

        val button8 = JButton("8")
        button8.addActionListener { typeChar('8') }
        gbc.gridx = 1
        gbc.gridy = 4
        contentPane.add(button8, gbc)

        val button9 = JButton("9")
        button9.addActionListener { typeChar('9') }
        gbc.gridx = 2
        gbc.gridy = 4
        contentPane.add(button9, gbc)

        val buttonEq = JButton("=")
        buttonEq.addActionListener { evaluateExpression() }
        gbc.gridx = 2
        gbc.gridy = 1
        contentPane.add(buttonEq, gbc)

        val buttonAdd = JButton("+")
        buttonAdd.addActionListener { typeChar('+') }
        gbc.gridx = 3
        gbc.gridy = 3
        contentPane.add(buttonAdd, gbc)

        val buttonSub = JButton("-")
        buttonSub.addActionListener { typeChar('-') }
        gbc.gridx = 3
        gbc.gridy = 2
        contentPane.add(buttonSub, gbc)

        val buttonBind = JButton("bind")
        buttonBind.addActionListener { addVariableBinding() }
        gbc.gridx = 0
        gbc.gridy = 5
        contentPane.add(buttonBind, gbc)

        val buttonMul = JButton("*")
        buttonMul.addActionListener { typeChar('*') }
        gbc.gridx = 3
        gbc.gridy = 4
        contentPane.add(buttonMul, gbc)

        val buttonDiv = JButton("/")
        buttonDiv.addActionListener { typeChar('/') }
        gbc.gridx = 3
        gbc.gridy = 5
        contentPane.add(buttonDiv, gbc)

        val buttonBrOpen = JButton("(")
        buttonBrOpen.addActionListener { typeChar('(') }
        gbc.gridx = 0
        gbc.gridy = 1
        contentPane.add(buttonBrOpen, gbc)

        val buttonBrClose = JButton(")")
        buttonBrClose.addActionListener { typeChar(')') }
        gbc.gridx = 1
        gbc.gridy = 1
        contentPane.add(buttonBrClose, gbc)

        val buttonDebug = JButton("debug")
        buttonDebug.addActionListener { displayDebugInfo() }
        gbc.gridx = 3
        gbc.gridy = 1
        contentPane.add(buttonDebug, gbc)

        gbc.gridx = 4
        gbc.gridy = 0
        gbc.gridheight = 6
        val bListGui = JList(bindingsList)
        contentPane.add(bListGui, gbc)
    }

    private fun evaluateExpression() {
        val expr: Expression
        try {
            expr = ParserImpl.INSTANCE.parseString(input.text)
        } catch (e: Exception) {
            result.text = "SynError"
            return
        }
        try {
            result.text = "=" + expr.accept(CalculatorVisitor(bindings)).toString()
        } catch (e: Exception) {
            result.text = "CompError"
        }
    }

    private fun typeChar(s: Char) {
        val str = StringBuilder(input.text).append(s)
        input.text = str.toString()
    }

    private fun reloadList() {
        bindingsList.clear()
        for ((variable, value) in bindings) {
            bindingsList.addElement("$variable = $value")
        }
    }

    private fun addVariableBinding() {
        val inputStr = JOptionPane.showInputDialog("Enter variable binding. For example, 'x=5'").split("=")
        if (inputStr.size != 2) {
            JOptionPane.showMessageDialog(this, "Invalid format")
            return
        }
        val name = inputStr[0]
        if (name.length != 1) {
            JOptionPane.showMessageDialog(this, "Variable name must be a letter")
            return
        }
        val nameChar = name[0]
        if (nameChar !in 'a'..'z') {
            JOptionPane.showMessageDialog(this, "Variable name must be a letter")
            return
        }
        try {
            bindings[nameChar] = inputStr[1].toDouble()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Invalid double value")
            return
        }
        reloadList()
    }

    private fun displayDebugInfo() {
        try {
            val resultStr = ParserImpl.INSTANCE.parseString(input.text).accept(DebugRepresentationVisitor.INSTANCE) as String
            JOptionPane.showMessageDialog(this, resultStr)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(this, "Invalid expression")
        }
    }
}

private fun initWindow() {
    val window = MainWindow()
    window.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::initWindow)
}

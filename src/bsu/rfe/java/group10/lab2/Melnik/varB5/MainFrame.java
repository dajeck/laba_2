package bsu.rfe.java.group10.lab2.Melnik.varB5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    private JTextField xField, yField, zField, resultField;
    private JRadioButton function1Button, function2Button;
    private JRadioButton var1Button, var2Button, var3Button;
    private Double mem1 = null, mem2 = null, mem3 = null;
    private double activeMemory = 0.0;
    private JLabel formulaImageLabel;

    public MainFrame() {
        setTitle("Калькулятор для функций");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        xField = new JTextField(10);
        yField = new JTextField(10);
        zField = new JTextField(10);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("x:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("y:"));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("z:"));
        inputPanel.add(zField);
        add(inputPanel);

        function1Button = new JRadioButton("Формула 1");
        function2Button = new JRadioButton("Формула 2");
        ButtonGroup functionGroup = new ButtonGroup();
        functionGroup.add(function1Button);
        functionGroup.add(function2Button);
        JPanel functionPanel = new JPanel();
        functionPanel.add(new JLabel("Выбирите функцию:"));
        functionPanel.add(function1Button);
        functionPanel.add(function2Button);
        add(functionPanel);

        function1Button.addActionListener(e -> displayFormulaImage("D:/учеба/java/lab_2/untitled/src/bsu/rfe/java/group10/lab2/Melnik/varB5/formula1.png"));
        function2Button.addActionListener(e -> displayFormulaImage("D:/учеба/java/lab_2/untitled/src/bsu/rfe/java/group10/lab2/Melnik/varB5/formula2.png"));

        var1Button = new JRadioButton("Переменная X");
        var2Button = new JRadioButton("Переменная Y");
        var3Button = new JRadioButton("Переменная Z");
        ButtonGroup varGroup = new ButtonGroup();
        varGroup.add(var1Button);
        varGroup.add(var2Button);
        varGroup.add(var3Button);
        JPanel varPanel = new JPanel();
        varPanel.add(new JLabel("Активная переменная:"));
        varPanel.add(var1Button);
        varPanel.add(var2Button);
        varPanel.add(var3Button);
        add(varPanel);

        resultField = new JTextField(15);
        resultField.setEditable(false);
        JButton calculateButton = new JButton("Вычислить");
        calculateButton.addActionListener(new CalculateButtonListener());
        JPanel resultPanel = new JPanel();
        resultPanel.add(calculateButton);
        resultPanel.add(resultField);
        add(resultPanel);

        JButton mcButton = new JButton("MC");
        JButton mPlusButton = new JButton("M+");
        mcButton.addActionListener(new MemoryClearButtonListener());
        mPlusButton.addActionListener(new MemoryAddButtonListener());
        JPanel memoryPanel = new JPanel();
        memoryPanel.add(mcButton);
        memoryPanel.add(mPlusButton);
        add(memoryPanel);

        formulaImageLabel = new JLabel();
        formulaImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(formulaImageLabel);

        setVisible(true);
    }

    private void displayFormulaImage(String imagePath) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            formulaImageLabel.setIcon(imageIcon);
        } else {
            formulaImageLabel.setIcon(null);
            JOptionPane.showMessageDialog(this,
                    "Изображение не найдено: " + imagePath,
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class CalculateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (mem1 == null)
                {
                    mem1 = Double.parseDouble(xField.getText());
                    mem2 = Double.parseDouble(yField.getText());
                    mem3 = Double.parseDouble(zField.getText());
                }
                double result = 0;
                if (function1Button.isSelected()) {
                    if(mem2 == -1 || mem1 <= 0){
                        JOptionPane.showMessageDialog(null, "Ошибка при вводе значений (y == -1 или x <= 0)");
                        SwingUtilities.invokeLater(MainFrame::new);
                    }
                    result = calculateFormula1();
                } else if (function2Button.isSelected()) {
                    if(mem1 == 0 || (mem3+mem2) <= 0){
                        JOptionPane.showMessageDialog(null, "Ошибка при вводе значений (x == 0 или (z+y) <= 0)");
                        SwingUtilities.invokeLater(MainFrame::new);
                    }
                    result = calculateFormula2();
                }

                resultField.setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Необходимо ввести все значения(x, y, z).");
            }
        }
    }

    private class MemoryClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (var1Button.isSelected()) {
                mem1 = 0.0;
            } else if (var2Button.isSelected()) {
                mem2 = 0.0;
            } else if (var3Button.isSelected()) {
                mem3 = 0.0;
            }
            updateResultFieldWithMemory();
        }
    }

    private class MemoryAddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double result = Double.parseDouble(resultField.getText());
            if (var1Button.isSelected()) {
                mem1 += result;
            } else if (var2Button.isSelected()) {
                mem2 += result;
            } else if (var3Button.isSelected()) {
                mem3 += result;
            }
            updateResultFieldWithMemory();
        }
    }

    private void updateResultFieldWithMemory() {
        if (var1Button.isSelected()) {
            resultField.setText(String.valueOf(mem1));
        } else if (var2Button.isSelected()) {
            resultField.setText(String.valueOf(mem2));
        } else if (var3Button.isSelected()) {
            resultField.setText(String.valueOf(mem3));
        }
    }

    private double calculateFormula1() {
        double x = mem1 ;
        double y = mem2 ;
        double z = mem3 ; // Math.pow() , sin() , cos(), sqrt()
        return Math.pow(
                        (Math.cos(Math.PI * Math.pow(x, 3)) + Math.log(Math.pow((1 + y) , 2)) ),
                        (0.25) )
                * ( Math.pow(Math.E, Math.pow(z, 2)) + Math.sqrt(1/x) + Math.cos(Math.pow(Math.E, y))
                    );
    }

    private double calculateFormula2() {
        double x = mem1 ;
        double y = mem2 ;
        double z = mem3 ;

        return Math.pow(Math.E, x*0.5) / (Math.sqrt(z + y) * Math.log(Math.pow(x, z)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

package ru.vsu.cs.dolzhenkoms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame {
    private JTable arrayTable;

    private JButton startTimerButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton doNextStepButton;
    private JButton executeSortButton;

    private JLabel infoLabel;

    private Timer timer;

    private Object[][] defaultArray = new Integer[][] {{7, 3, 5, 4}};
    private List<SortState> steps;

    private int stepNumber = 0;

    public MainWindow() {
        super("WindowUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        startTimerButton = new JButton("Запустить таймер");
        addButton = new JButton("Добавить ячейку");
        removeButton = new JButton("Убрать ячейку");
        doNextStepButton = new JButton("Следующий шаг");
        executeSortButton = new JButton("Начать сортировку");
        infoLabel = new JLabel();

        initTimer();
        initButtons();
        initMarkup();

        setSize(600, 300);
        setVisible(true);
    }

    private void initTimer() {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNextStep();
            }
        });
    }

    private void initButtons() {
        startTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        executeSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) arrayTable.getModel();
                SortState state = new SortState(ArrayUtils.getArrayFromTable(model));
                state.gnomeSort();
                steps = state.getSteps();

                System.out.println(steps.size());
                stepNumber = 0;
                startTimer();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arrayTable != null) {
                    DefaultTableModel model = (DefaultTableModel) arrayTable.getModel();
                    model.setColumnCount(model.getColumnCount() + 1);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arrayTable != null) {
                    DefaultTableModel model = (DefaultTableModel) arrayTable.getModel();
                    model.setColumnCount(model.getColumnCount() - 1);
                }
            }
        });

        doNextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    doNextStep();
            }
        });
    }

    private void initMarkup() {
        DefaultTableModel model = new DefaultTableModel(defaultArray, new String[defaultArray[0].length]);

        arrayTable = new JTable(model);
        arrayTable.setRowHeight(30);

        Box workPlace = new Box(BoxLayout.Y_AXIS);


        JPanel rowButtons = new JPanel();
        rowButtons.add(addButton);
        rowButtons.add(removeButton);

        workPlace.add(arrayTable);
        workPlace.add(rowButtons);
        workPlace.add(infoLabel);
        workPlace.add(executeSortButton);
        workPlace.add(startTimerButton);
        workPlace.add(doNextStepButton);
        getContentPane().add(workPlace);
    }

    private void doNextStep() {
        if(stepNumber == steps.size()) {
            JOptionPane.showMessageDialog(null, "Массив отсортирован");
            stepNumber = 0;
        }

        SortState currentStep = steps.get(stepNumber);
        int i = currentStep.getI();
        int j = currentStep.getJ();
        int[] array = currentStep.getArray();

        DefaultTableModel model = (DefaultTableModel) arrayTable.getModel();

        int[][] doubleArray = new int[1][];
        doubleArray[0] = array;

        ArrayUtils.fillTableModelBy2Array(model, doubleArray);

        StringBuilder info = new StringBuilder();

        info.append("Шаг №" + (i + 1) + ". i = " + i + " j = " + j + "\n");
        info.append(currentStep.isSorted() ? "Отсорирован" : "Сортируется");

        infoLabel.setText(info.toString());

        stepNumber++;
    }

    private void startTimer() {
        if(timer.isRunning()) {
            timer.stop();
            startTimerButton.setText("Запустить таймер");
            doNextStepButton.setEnabled(true);
            addButton.setEnabled(true);
            removeButton.setEnabled(true);
        }
        else {
            timer.start();
            startTimerButton.setText("Остановить таймер");
            doNextStepButton.setEnabled(false);
            addButton.setEnabled(false);
            removeButton.setEnabled(false);
        }
    }

}

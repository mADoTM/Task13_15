package ru.vsu.cs.dolzhenkoms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIMain extends JFrame {
    private static Game game;

    private JTable tableField;
    private JMenuBar menu;

    private JLabel labelGoal;
    private JLabel labelScore;

    public GUIMain() {
        super("WindowUI");
        game = new Game();
        labelGoal = new JLabel("Цель - " + game.getMaxScore() + ". ");
        labelScore = new JLabel("Ваш счёт - " + game.getScore() + ".");

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initJMenu();
        initFieldTable();

        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(tableField);
        mainBox.add(labelGoal);
        mainBox.add(labelScore);

        setJMenuBar(menu);
        getContentPane().add(mainBox);

        setSize(600, 400);
        setVisible(true);
    }

    public void startNewGame() {
        game.restartGame();
        repaintContent();
    }

    private void initFieldTable() {

        tableField = new JTable(getTableModelFromField());
        tableField.setRowHeight(30);

        tableField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableField.rowAtPoint(e.getPoint());
                int col = tableField.columnAtPoint(e.getPoint());
                game.leftMouseClick(col,row);
                labelScore.setText("Ваш счёт - " + game.getScore() + ".");
                tableField.setModel(getTableModelFromField());
            }
        });
    }

    private void repaintContent() {
        labelGoal.setText("Цель - " + game.getMaxScore() + ".");
        labelScore.setText("Ваш счёт - " + game.getScore() + ".");
        tableField.setModel(getTableModelFromField());
    }

    private void initJMenu() {
        menu = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");

        JMenuItem newGameItem = new JMenuItem("Новая игра");
        JMenuItem settingsItem = new JMenuItem("Настройки");

        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        settingsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsWindow(game);
            }
        });

        gameMenu.add(newGameItem);
        gameMenu.add(settingsItem);
        menu.add(gameMenu);

        menu.add(Box.createHorizontalGlue());
    }

    private DefaultTableModel getTableModelFromField() {
        Object[][] values = new Object[game.getField().length][game.getField()[0].length];
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values[i].length; j++) {
                switch (game.getField()[i][j].getStatus()) {
                    case PLAYER: values[i][j] = "\uD83D\uDE01"; break;
                    case DEFAULT: values[i][j] = game.getField()[i][j].getValue();
                        break;
                    case BOMB: values[i][j] = "✹"; break;
                    case OPENED:
                        values[i][j] = "*";
                        break;
                    case DEAD:
                        values[i][j] = " ☹ ";
                        break;
                    default: break;
                }
            }
        }


        DefaultTableModel tableModel = new DefaultTableModel(values, values[0]);

        return tableModel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

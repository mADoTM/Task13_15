package ru.vsu.cs.dolzhenkoms;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private Game game;

    private JButton saveSettingsButton;

    private JSlider heightSlider;
    private JSlider widthSlider;

    private JLabel heightLabel;
    private JLabel widthLabel;

    private int height;
    private int width;

    public SettingsWindow(Game game) {
        super("Настройки");

        this.game = game;
        this.height = game.getField().length;
        this.width = game.getField()[0].length;

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initButton();

        Box mainBox = new Box(BoxLayout.Y_AXIS);

        heightLabel = new JLabel("Высота - " + height);
        widthLabel = new JLabel("Длина - " + width);

        initSliders();

        mainBox.add(heightLabel);
        mainBox.add(heightSlider);
        mainBox.add(widthLabel);
        mainBox.add(widthSlider);
        mainBox.add(saveSettingsButton);

        getContentPane().add(mainBox);

        setSize(100,150);
        setVisible(true);
    }

    private void initButton() {
        saveSettingsButton = new JButton("Сохранить настройки");

        saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.customInitializeField(height,width);
                dispose();
            }
        });
    }

    private void initSliders() {
        heightSlider = new JSlider(6,40);
        heightSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider slider = (JSlider) evt.getSource();
                if (!slider.getValueIsAdjusting()) {
                    height = slider.getValue();
                    heightLabel.setText("Высота - " + height);
                }
            }
        });

        widthSlider = new JSlider(6,40);
        widthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider slider = (JSlider) evt.getSource();
                if (!slider.getValueIsAdjusting()) {
                    width = slider.getValue();
                    widthLabel.setText("Длина - " + width);
                }
            }
        });
    }
}

package core;

import javax.swing.*;

public class startPage extends JFrame {

    public static void main(String[] args) {
        new startPage();
    }

    startPage() {
        add(new gamePage());
        setTitle("Jogo da Cobrinha - Snake game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

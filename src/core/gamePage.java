package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class gamePage extends JPanel implements ActionListener {

    private static final int screen_width = 1300;
    private static final int screen_height = 750;
    private static final int tile_size = 50;
    private static final int unities = screen_width * screen_height / (tile_size * tile_size);
    private static final int delay = 200;
    private static final String font_name = "Ink Free";
    private final int[] x_axis = new int[unities];
    private final int[] y_axis = new int[unities];
    private int snakeBody = 6;
    private int points;
    private int x_tile;
    private int y_tile;
    private char direction = 'D'; // W - Cima, S - Baixo, A - Esquerda, D - Direita
    private boolean isRunning = false;
    Timer timer;
    Random random;

    gamePage() {
        random = new Random();
        setPreferredSize(new Dimension(screen_width, screen_height));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(new keyAdapter());
        playGame();
    }

    public void playGame() {
        createTile();
        isRunning = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScreen(g);
    }

    public void drawScreen(Graphics g) {
        if (isRunning) {
            g.setColor(Color.red);
            g.fillOval(x_tile, y_tile, tile_size, tile_size);

            for (int i = 0; i < snakeBody; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x_axis[0], y_axis[0], tile_size, tile_size);
                } else {
                    g.setColor(new Color(45, 100, 0));
                    g.fillRect(x_axis[i], y_axis[i], tile_size, tile_size);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font(font_name, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos:" + points, (screen_width - metrics.stringWidth("Pontos:" + points)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    private void createTile() {
        x_tile = random.nextInt(screen_width / tile_size) * tile_size;
        y_tile = random.nextInt(screen_height / tile_size) * tile_size;
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font(font_name, Font.BOLD, 40));
        FontMetrics pontuationFont = getFontMetrics(g.getFont());
        g.drawString("Pontos:" + points, (screen_width - pontuationFont.stringWidth("Pontos:" + points)) / 2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font(font_name, Font.BOLD, 75));
        FontMetrics finalFont = getFontMetrics(g.getFont());
        g.drawString("\uD83D\uDE1D Fim do Jogo.", (screen_width - finalFont.stringWidth("Fim do Jogo")) / 2, screen_height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (isRunning) {
            walk();
            getTile();
            checkBound();
        }
        repaint();
    }

    private void walk() {
        for (int i = snakeBody; i > 0; i--) {
            x_axis[i] = x_axis[i - 1];
            y_axis[i] = y_axis[i - 1];
        }

        switch (direction) {
            case 'W':
                y_axis[0] = y_axis[0] - tile_size;
                break;
            case 'S':
                y_axis[0] = y_axis[0] + tile_size;
                break;
            case 'A':
                x_axis[0] = x_axis[0] - tile_size;
                break;
            case 'D':
                x_axis[0] = x_axis[0] + tile_size;
                break;
            default:
                break;
        }
    }

    private void getTile() {
        if (x_axis[0] == x_tile && y_axis[0] == y_tile) {
            snakeBody++;
            points++;
            createTile();
        }
    }

    private void checkBound() {
        //A cabeça bateu no corpo?
        for (int i = snakeBody; i > 0; i--) {
            if (x_axis[0] == x_axis[i] && y_axis[0] == y_axis[i]) {
                isRunning = false;
                break;
            }
        }

        //A cabeça tocou uma das bordas Direita ou esquerda?
        if (x_axis[0] < 0 || x_axis[0] > screen_width) {
            isRunning = false;
        }

        //A cabeça tocou o piso ou o teto?
        if (y_axis[0] < 0 || y_axis[0] > screen_height) {
            isRunning = false;
        }

        if (!isRunning) {
            timer.stop();
        }
    }

    public class keyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'D') {
                        direction = 'A';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'A') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'S') {
                        direction = 'W';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'W') {
                        direction = 'S';
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

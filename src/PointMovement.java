
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;

public class PointMovement extends JFrame {
    private JPanel panel;
    private JButton increaseButton;
    private JButton decreaseButton;
    private int speed = 5;
    private int x = 0;
    private int y = 0;

    public PointMovement() {
        setTitle("Управління швидкістю руху точки");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.PINK);
                g.fillOval(x, y, 10, 10);
            }
        };

        increaseButton = new JButton("Збільшити швидкість");
        decreaseButton = new JButton("Зменшити швидкість");

        increaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speed += 1; // Збільшуємо швидкість на 1
            }
        });

        decreaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (speed > 0) {
                    speed -= 1; // Зменшуємо швидкість на 1 (якщо вона більша за 0)
                }
            }
        });

        panel.add(increaseButton);
        panel.add(decreaseButton);

        add(panel);

        setVisible(true);

        // Створюємо таймер, який буде оновлювати положення точки з відповідною швидкістю
        Timer timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                x += speed;
                y += speed;
                panel.repaint();
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PointMovement();
            }
        });
    }
}

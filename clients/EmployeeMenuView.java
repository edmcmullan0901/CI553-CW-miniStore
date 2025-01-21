package clients;

import middle.MiddleFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeMenuView extends JFrame
{   private JButton cashierButton;
    private JButton packingButton;
    private JButton backDoorButton;


    public EmployeeMenuView() {
       setTitle("Employee Menu");
       setSize(800, 600);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(new BorderLayout());


        JPanel mainPanel = UIStyler.createStyledPanel(new GridLayout(3, 1, 10, 10));

        cashierButton = UIStyler.createStyledButton("Cashier");
        mainPanel.add(cashierButton);

        packingButton = UIStyler.createStyledButton("Packing");
        mainPanel.add(packingButton);

        backDoorButton = UIStyler.createStyledButton("Back Door");
        mainPanel.add(backDoorButton);

        add(mainPanel, BorderLayout.CENTER);

        addSoundOnButtonPress(cashierButton);
        addSoundOnButtonPress(packingButton);
        addSoundOnButtonPress(backDoorButton);


    }

    private void addSoundOnButtonPress(JButton button) {
        button.addActionListener(e -> {
            SoundPlayer.playSound("audio/button_press.wav");
        });
    }

    public void addCashierButtonListener(ActionListener listener) {
        cashierButton.addActionListener(listener);
    }

    public void addPackingButtonListener(ActionListener listener) {
        packingButton.addActionListener(listener);
    }
    public void addBackDoorListener(ActionListener listener) {
        backDoorButton.addActionListener(listener);

    }

}

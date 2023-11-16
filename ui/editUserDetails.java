package ui;

import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editUserDetails {
    private JPanel rootPanel;
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JTextField textField1;
    private JTextField houseNoField;
    private JTextField streetNameField;
    private JTextField postcodeField;
    private JTextField countryField;
    private JTextField cardNoField;
    private JTextField cardNameField;
    private JTextField expiryDateField;
    private JButton backToDashboardButton;
    private JButton saveButton;


    public editUserDetails(User user) {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        forenameField.setText(user.getForename());
        surnameField.setText(user.getSurname());
        emailField.setText(user.getEmail());

    }
}

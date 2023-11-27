package ui;

import javax.swing.*;

import App.App;
import user.User;
import database.database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class managerDashboard {
    private JTextField emailField;
    private JButton makeUserAStaffButton;
    private JPanel staffList;
    public JPanel rootPanel;
    private JList staff;
    private JButton backToStaffDashboardButton;
    private JButton demoteSelectedStaffButton;

    private List<User> allStaffUsers;
    private List<String> staffDetails;

    public managerDashboard(User user, JFrame frame) {
        //Populate JList with staff users
        loadStaffUsers();
        staff.setListData(staffDetails.toArray());

        makeUserAStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get user from email
                String enteredEmail = emailField.getText();
                User userToPromote = null;
                for (User u: User.users) {
                    if (Objects.equals(u.getEmail(), enteredEmail)) {
                        userToPromote = u;
                        break;
                    }
                }
                //Check if user exists and is non-staff
                if (userToPromote != null && userToPromote.getIsStaff() == 0) {
                    //Connect to database
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection(database.url, database.username, database.password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    String updateQuery = ("UPDATE users SET isStaff = 1 WHERE userID = " + userToPromote.getId() + ";");
                    //Update the database
                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Reset JList
                    try {
                        User.reloadUserArray();
                        loadStaffUsers();
                        staff.setListData(staffDetails.toArray());
                        emailField.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist or is already staff.");
                }
            }
        });

        demoteSelectedStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if a staff has been selected
                if(staff.getSelectedIndex() != -1)  {
                    //Get staffID
                    int staffID = allStaffUsers.get(staff.getSelectedIndex()).getId();
                    //Connect to database
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection(database.url, database.username, database.password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    String updateQuery = ("UPDATE users SET isStaff = 0 WHERE userID = " + staffID + ";");
                    //Update the database
                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Reset JList
                    try {
                        User.reloadUserArray();
                        loadStaffUsers();
                        staff.setListData(staffDetails.toArray());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        backToStaffDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.staffDashboard(user);
            }
        });
    }

    //Fills the lists with all current staff users (excludes manager)
    private void loadStaffUsers() {
        allStaffUsers = new ArrayList<>();
        staffDetails = new ArrayList<>();
        for (User u: User.users) {
            if (u.getIsStaff() == 1 && u.getIsManager() == 0) {
                allStaffUsers.add(u);
                staffDetails.add(u.getForename()+ " | " + u.getSurname() + " | " + u.getEmail());
            }
        }
    }
}

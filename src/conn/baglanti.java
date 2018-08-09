package conn;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import pojos.users;

/**
 *
 * @author suleyman
 */
public class baglanti {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    static int sayac = 1;
    static int sayac_driver = 1;

    public String baglan() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String query = "jdbc:sqlserver://95.173.170.161;databaseName=muzafferelgun_TALASBIM;"
//                  + "user=muzafferelgun_15;password=150015Melgun";
            String query = "jdbc:sqlserver://127.0.0.1;databaseName=notification;user=sa;password=2472";
            //String query = "jdbc:sqlserver://ASUS-PC\\SQLSERVER;databaseName=notification;integratedSecurity=true";
            con = DriverManager.getConnection(query);
            return "true";
        } catch (ClassNotFoundException | SQLException e) {
            if(sayac_driver==1)
            {
                popUP("Hata..!!", e.getMessage());
            }
            sayac_driver++;
            //System.out.println(e.getMessage());
            return "false";
        }
    }

    public ArrayList<users> kontrol(String user_name) {
        baglan();
        ArrayList<users> list = new ArrayList<>();
        list.clear();
        try {
            ps = con.prepareStatement("select * from users "
                    + "where user_name like '%" + user_name + "%' and is_notify='1'");
            rs = ps.executeQuery();
            String snc = "";
            while (rs.next()) {
                users u = new users(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getInt(4), rs.getString(5),
                        rs.getString(6),
                        rs.getString(7), rs.getDate(8));
                notify_haveseen(user_name, rs.getString(5), rs.getString(6));
                list.add(u);
            }
            return list;
        } catch (SQLException e) {
            if (sayac == 1) {
                popUP("Hata..!!",e.getMessage());
                //JOptionPane.showMessageDialog(null, e.getMessage(),"Hata!!",JOptionPane.ERROR_MESSAGE);
            }
            sayac++;
            return new ArrayList<>();
        }
    }

    public String getUrl(String user_name) {
        baglan();
        try {
            ps = con.prepareStatement("select notify_url from users "
                    + "where user_name like '%?%' and is_notify='1'");
            ps.setString(1, user_name);
            rs = ps.executeQuery();
            rs.next();
            String url = rs.getString(1);
            if (url != null) {
                return url;
            } else {
                return "";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public void notify_haveseen(String user_name, String n_text, String n_header) {
        baglan();
        PreparedStatement pres;
        ResultSet res;
        try {
            pres = con.prepareStatement("update users set is_notify='0' where user_name=? "
                    + "and notify_text=? and notify_header=?");
            pres.setString(1, user_name);
            pres.setString(2, n_text);
            pres.setString(3, n_header);
            pres.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public void popUP(String header,String message)
    {
        JFrame frame = new JFrame();
        frame.setSize(400, 175);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel headingLabel = new JLabel(header);
        Icon headingIcon = null;
        headingLabel.setIcon(headingIcon); // --- use image icon you want to be as heading image.
        headingLabel.setOpaque(false);
        headingLabel.setFont(new Font("The News Roman", Font.BOLD, 18));
        frame.add(headingLabel, constraints);
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.PAGE_START;
        JButton cloesButton = new JButton("X");
        cloesButton.setMargin(new Insets(1, 4, 1, 4));
        cloesButton.setFocusable(false);
        cloesButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
            }
        });
        frame.add(cloesButton, constraints);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel("<HtMl>" + message);
        messageLabel.setFont(new Font("The News Roman", Font.PLAIN, 17));
        messageLabel.setForeground(Color.BLACK);
        frame.add(messageLabel, constraints);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
        frame.setLocation(scrSize.width - frame.getWidth() - 5,
                scrSize.height - toolHeight.bottom - frame.getHeight() - 7);
    }
}
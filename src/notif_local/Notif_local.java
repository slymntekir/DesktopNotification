package notif_local;

import conn.baglanti;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import com.sun.security.auth.module.NTSystem;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.Icon;
import pojos.users;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notif_local {
    
    public static void popUp(String header, String message, String url,int sayi) throws URISyntaxException {
        // String message
        //  = "You got a new notification message.Isnt it awesome to have such a notification message";
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

        final URI uri = new URI(url);
        constraints.gridx=0;
        constraints.gridy++;
        constraints.weightx=1.0f;
        constraints.weighty=1.0f;
        constraints.insets=new Insets(1, 1, 2, 1);
        constraints.fill=GridBagConstraints.LAST_LINE_START;
        constraints.anchor=GridBagConstraints.PAGE_END;
        JLabel l = new JLabel(url);
        l.setFont(new Font("The News Roman", Font.ITALIC, 16));
        l.setForeground(Color.BLUE);
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                open(uri);
            }
        });
        frame.add(l,constraints);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
        frame.setLocation(scrSize.width - frame.getWidth() - 5,
                scrSize.height - toolHeight.bottom - sayi*frame.getHeight() - (7+sayi));
    }

    public static void main(String[] args) 
    {
        baglanti b = new baglanti();
        b.baglan();

        ScheduledExecutorService execSer = Executors.newScheduledThreadPool(5);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ArrayList<users> lists = new ArrayList<>();
                lists.clear();
                lists = b.kontrol(getPcCurrentName());
                System.err.println(new Date());
                
                //if(!lists.isEmpty()) {
                    showMessage(lists);
                //}
            }
        };
        execSer.scheduleWithFixedDelay(r,0,5,TimeUnit.SECONDS);
    }
    
    public static void showMessage(ArrayList<users> l) {
        try {
            for (int i = 0; i < l.size(); i++) {
                popUp(l.get(i).getNotify_header() + " - " + getPcName() + " - "
                        + getPcCurrentName(), l.get(i).getNotify_text(),
                        l.get(i).getNotify_url(),i+1);
            }
        } catch (URISyntaxException e) {}
    }

    // bilgisayar adı
    public static String getPcName() {
        String hostname;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            //System.out.println(hostname);
            return hostname;
        } catch (UnknownHostException ex) {
            //System.out.println("Hostname can not be resolved");
            return ex.getMessage();
        }
    }

    // mevcut kullanıcı adı
    public static String getPcCurrentName() {
        NTSystem nt_sys = new NTSystem();
        return nt_sys.getName();
    }

    public static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {}
        }
    }
}
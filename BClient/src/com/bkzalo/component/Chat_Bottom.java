package com.bkzalo.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.bkzalo.app.MessageType;
import com.bkzalo.event.PublicEvent;
import com.bkzalo.model.Model_Message;
import com.bkzalo.model.Model_User;
import com.bkzalo.service.Service;
import com.bkzalo.swing.JIMSendTextPane;
import com.bkzalo.swing.ScrollBar;
import com.bkzalo.component.Panel_More;

import net.miginfocom.swing.MigLayout;

public class Chat_Bottom extends javax.swing.JPanel {
	

    private MigLayout mig;
    private Panel_More panelMore;
    
    private Model_User user;

    public Model_User getUser() {
        return user;
    }

    public void setUser(Model_User user) {
        this.user = user;
        panelMore.setUser(user);
    }

    public Chat_Bottom() {
        initComponents();
        init();
    }

    private void init() {
        mig = new MigLayout("fillx, filly", "0[fill]0[]0[]2", "2[fill]2[]0");
        setLayout(mig);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        
        //Text
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                refresh();
                if (ke.getKeyChar() == 10 && ke.isControlDown()) {
                    //  user press controll + enter
                    eventSend(txt);
                }
            }
        });
        txt.setBorder(new EmptyBorder(5, 5, 5, 5));
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        
        //Scroll
        ScrollBar sb = new ScrollBar();
        sb.setBackground(new Color(229, 229, 229));
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(sb);
        add(scroll, "w 100%");
        
        //Panel contain cmd and cmdMore
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[]3[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        panel.setBackground(Color.WHITE);
        
        //Button send
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/send.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                eventSend(txt);
            }
        });
        
        //Button "..."
        JButton cmdMore = new JButton();
        cmdMore.setBorder(null);
        cmdMore.setContentAreaFilled(false);
        cmdMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/more_disable.png")));
        cmdMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panelMore.isVisible()) {
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/more_disable.png")));
                    panelMore.setVisible(false);
                    mig.setComponentConstraints(panelMore, "dock south,h 0!");
                    revalidate();
                } else {
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/more.png")));
                    panelMore.setVisible(true);
                    mig.setComponentConstraints(panelMore, "dock south,h 170!");
                    revalidate();
                }
            }
        });
        
        panel.add(cmdMore);
        panel.add(cmd);
        add(panel, "wrap");
        panelMore = new Panel_More();
        panelMore.setVisible(false);
        add(panelMore, "dock south,h 0!");  //  set height 0
    }

    private void eventSend(JIMSendTextPane txt) {
    	String text = txt.getText().trim();
        if (!text.equals("")) {
        	Model_Message mgs= new Model_Message();
        	mgs.setType(MessageType.TEXT);
        	mgs.setIdUserSend(Service.Instance().getUser().getId());
        	mgs.setIdUserReceive(user.getId());
        	mgs.setText(text);
            send(mgs);//message
            PublicEvent.getInstance().getEventChat().sendMessage(mgs);//message
            txt.setText("");
            txt.grabFocus();
            refresh();
        } else {
            txt.grabFocus();
        }
    }

    private void send(Model_Message mgs) {
        Service.Instance().getClient().emit("send_to_user", mgs.toJsonObject());
    }

    private void refresh() {
        revalidate();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setBackground(new java.awt.Color(229, 229, 229));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
    }
}

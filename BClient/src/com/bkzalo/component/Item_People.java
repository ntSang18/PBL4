package com.bkzalo.component;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.bkzalo.event.PublicEvent;
import com.bkzalo.model.Model_LoadChat;
import com.bkzalo.model.Model_User;
import com.bkzalo.service.Service;

public class Item_People extends javax.swing.JPanel {
	
    private com.bkzalo.swing.ActiveStatus activeStatus;
    private com.bkzalo.swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbStatus;
	
    private boolean mouseOver;
    private Model_User user;

    public Model_User getUser() {
        return user;
    }

    public Item_People(Model_User user) { //Model_User_Account user
        this.user = user;
        //sang was fixed
        if(user != null) {
        	initComponents();
            lb.setText(user.getUsername());
            activeStatus.setActive(user.isStatus());
            init();
        }
    }

    public void updateStatus() {
        activeStatus.setActive(user.isStatus());
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(230, 230, 230));
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(new Color(242, 242, 242));
                mouseOver = false;
            }
            //Ham chon user chat
            @Override
            public void mouseReleased(MouseEvent me) {
                if (mouseOver) {
                	Service.Instance().LoadChat(user.getId());
                    PublicEvent.getInstance().getEventMain().selectUser(user);
                    
                }
            }
        });
    }
    

    @SuppressWarnings("unchecked")
    private void initComponents() {

        imageAvatar1 = new com.bkzalo.swing.ImageAvatar();
        lb = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        activeStatus = new com.bkzalo.swing.ActiveStatus();

        setBackground(new java.awt.Color(242, 242, 242));

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/user.png"))); // NOI18N

        lb.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lb.setText("Name");

        lbStatus.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(117, 117, 117));
        lbStatus.setText("New User");

        activeStatus.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbStatus)
                        .addGap(3, 3, 3)
                        .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(activeStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );
    }
}

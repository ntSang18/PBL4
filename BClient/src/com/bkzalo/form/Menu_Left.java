package com.bkzalo.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import com.bkzalo.component.Item_People;
import com.bkzalo.event.EventMenuLeft;
import com.bkzalo.event.PublicEvent;
import com.bkzalo.model.Model_User;
import com.bkzalo.swing.ScrollBar;

import net.miginfocom.swing.MigLayout;

public class Menu_Left extends javax.swing.JPanel {

    private javax.swing.JLayeredPane menu;
    private com.bkzalo.component.MenuButton menuBox;
    private com.bkzalo.component.MenuButton menuGroup;
    private javax.swing.JLayeredPane menuList;
    private com.bkzalo.component.MenuButton menuMessage;
    private javax.swing.JScrollPane sp;

    private List<Model_User> ListUser;

    public Menu_Left() {
        initComponents();
        init();
    }

    private void init() {
        sp.setVerticalScrollBar(new ScrollBar());
        menuList.setLayout(new MigLayout("fillx", "0[]0", "0[]0"));
        ListUser = new ArrayList<>();
        PublicEvent.getInstance().addEventMenuLeft(new EventMenuLeft() {
            @Override
            public void listUser(List<Model_User> users) { //List<Model_User_Account> users
                for (Model_User d : users) {
                	ListUser.add(d);
                    menuList.add(new Item_People(d), "wrap");
                    refreshMenuList();
                }
            }

            @Override
            public void userConnect(int userid) { //int userID
                for (Model_User u : ListUser) {
                   if (u.getId() == userid) {
                        u.setStatus(true);
                        //Khi user connect, goi su kien update de lam moi form chat_title
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                //Cap nhat trang thai Listuser Menu_Left
                if (menuMessage.isSelected()) {
                    for (Component com : menuList.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getId() == userid) {
                        	item.getUser().setStatus(true);
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }

            @Override
            public void userDisconnect(int userId) { //int userID
                for (Model_User u : ListUser) {
                    if (u.getId() == userId) {
                        u.setStatus(false);
                      //Khi user disconnect, goi su kien update de lam moi form chat_title
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                if (menuMessage.isSelected()) {
                    for (Component com : menuList.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getId() == userId) {
                        	item.getUser().setStatus(false);
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }
            });
        showMessage();
    }

    private void showMessage() {
        //  test data
        menuList.removeAll();
//        for (Model_User_Account d : ListUserAccount) {
//        	//Sang make it work
//            menuList.add(new Item_People(d), "wrap");
//        }
        refreshMenuList();
    }

    private void showGroup() {
        //  test data
        menuList.removeAll();
//        for (int i = 0; i < 5; i++) {
//            menuList.add(new Item_People(null), "wrap");
//        }
        refreshMenuList();
    }

    private void showBox() {
        //  test data
        menuList.removeAll();
//        for (int i = 0; i < 10; i++) {
//            menuList.add(new Item_People(null), "wrap");
//        }
        refreshMenuList();
    }

    private void refreshMenuList() {
        menuList.repaint();
        menuList.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JLayeredPane();
        menuMessage = new com.bkzalo.component.MenuButton();
        menuGroup = new com.bkzalo.component.MenuButton();
        menuBox = new com.bkzalo.component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(242, 242, 242));

        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 3));

        menuMessage.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/message_selected.png"))); // NOI18N
        menuMessage.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/message.png"))); // NOI18N
        menuMessage.setSelected(true);
        menuMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMessageActionPerformed(evt);
            }
        });
        menu.add(menuMessage);

        menuGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/group.png"))); // NOI18N
        menuGroup.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/group_selected.png"))); // NOI18N
        menuGroup.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/group.png"))); // NOI18N
        menuGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGroupActionPerformed(evt);
            }
        });
        menu.add(menuGroup);

        menuBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/box.png"))); // NOI18N
        menuBox.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/box_selected.png"))); // NOI18N
        menuBox.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/com/bkzalo/icon/box.png"))); // NOI18N
        menuBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBoxActionPerformed(evt);
            }
        });
        menu.add(menuBox);

        sp.setBackground(new java.awt.Color(242, 242, 242));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(242, 242, 242));
        menuList.setOpaque(true);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMessageActionPerformed
        if (!menuMessage.isSelected()) {
            menuMessage.setSelected(true);
            menuGroup.setSelected(false);
            menuBox.setSelected(false);
            showMessage();
        }
    }//GEN-LAST:event_menuMessageActionPerformed

    private void menuGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGroupActionPerformed
        if (!menuGroup.isSelected()) {
            menuMessage.setSelected(false);
            menuGroup.setSelected(true);
            menuBox.setSelected(false);
            showGroup();
        }
    }//GEN-LAST:event_menuGroupActionPerformed

    private void menuBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBoxActionPerformed
        if (!menuBox.isSelected()) {
            menuMessage.setSelected(false);
            menuGroup.setSelected(false);
            menuBox.setSelected(true);
            showBox();
        }
    }//GEN-LAST:event_menuBoxActionPerformed
}

package com.bkzalo.component;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.bkzalo.app.MessageType;
import com.bkzalo.emoji.Emogi;
import com.bkzalo.emoji.Model_Emoji;
import com.bkzalo.event.PublicEvent;
import com.bkzalo.main.MainClient;
import com.bkzalo.model.Model_Message;
import com.bkzalo.model.Model_User;
import com.bkzalo.service.Service;
import com.bkzalo.swing.ScrollBar;
import com.bkzalo.swing.WrapLayout;
import com.bkzalo.component.OptionButton;

import net.miginfocom.swing.MigLayout;

public class Panel_More extends javax.swing.JPanel {
	
    private JPanel panelHeader;
    private JPanel panelDetail;
    
    private Model_User user;

    public Model_User getUser() {
        return user;
    }

    public void setUser(Model_User user) {
        this.user = user;
    }



    public Panel_More() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx"));
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.LINE_AXIS));
        panelHeader.add(getButtonImage());
        panelHeader.add(getButtonFile());
        panelHeader.add(getButtonCall());
        panelHeader.add(getButtonVideoCall());
        panelHeader.add(getEmojiStyle1());
        panelHeader.add(getEmojiStyle2());
        add(panelHeader, "w 100%, h 30!, wrap");
        panelDetail = new JPanel();
        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));    //  use warp layout
        JScrollPane ch = new JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new ScrollBar());
        //  test color
        add(ch, "w 100%, h 100%");
    }

    private JButton getButtonCall() {
    	OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/call.png")));
        return cmd;
    }
    
    private JButton getButtonVideoCall() {
    	OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/videocall.png")));
        return cmd;
    }
    
    private JButton getButtonImage() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/image.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser ch = new JFileChooser();
                ch.setMultiSelectionEnabled(false);
                ch.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                    	if(file.isDirectory() || isImageFile(file))
                    	{
                    		return true;
                    	}
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "Image File";
                    }
                });
                int option = ch.showOpenDialog(MainClient.getFrames()[0]);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = ch.getSelectedFile();
					Model_Message mgs = new Model_Message();
					mgs.setIdUserSend(Service.Instance().getUser().getId());
					mgs.setIdUserReceive(user.getId());
					mgs.setType(MessageType.IMAGE);
					mgs.setFile(file);
					mgs.setFilename(file.getName());
					Service.Instance().InitFile(mgs);
					System.out.println("image button");
					PublicEvent.getInstance().getEventChat().sendMessage(mgs);
                }
            }
        });
        return cmd;
    }

    private JButton getButtonFile() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/bkzalo/icon/link.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	JFileChooser ch = new JFileChooser();
                ch.setMultiSelectionEnabled(false);
                ch.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                    	if(file.isDirectory() || isFile(file))
                    	{
                    		return true;
                    	}
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "File";
                    }
                });
                int option = ch.showOpenDialog(MainClient.getFrames()[0]);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = ch.getSelectedFile();
					Model_Message mgs = new Model_Message();
					mgs.setIdUserSend(Service.Instance().getUser().getId());
					mgs.setIdUserReceive(user.getId());
					mgs.setType(MessageType.FILE);
					mgs.setFile(file);
					mgs.setFilename(file.getName());
					Service.Instance().InitFile(mgs);
					System.out.println("file button");
					PublicEvent.getInstance().getEventChat().sendMessage(mgs);
                }
            }
        });
        return cmd;
    }

    private JButton getEmojiStyle1() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(Emogi.getInstance().getImoji(1).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emogi.getInstance().getStyle1()) {
                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }
        });
        return cmd;
    }

    private JButton getEmojiStyle2() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(Emogi.getInstance().getImoji(21).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emogi.getInstance().getStyle2()) {
                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }
        });
        return cmd;
    }

    private JButton getButton(Model_Emoji data) {
        JButton cmd = new JButton(data.getIcon());
        cmd.setName(data.getId() + "");
        cmd.setBorder(new EmptyBorder(3, 3, 3, 3));
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setContentAreaFilled(false);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Model_Message mgs = new Model_Message();
                mgs.setIdUserSend(Service.Instance().getUser().getId());
                mgs.setIdUserReceive(user.getId());
                mgs.setType(MessageType.EMOJI);
                mgs.setText(String.valueOf(data.getId()));

                sendMessage(mgs);
                PublicEvent.getInstance().getEventChat().sendMessage(mgs);
            }
        });
        return cmd;
    }

    private void sendMessage(Model_Message mgs) {
        Service.Instance().getClient().emit("send_to_user", mgs.toJsonObject());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
    }

    private void clearSelected() {
        for (Component c : panelHeader.getComponents()) {
            if (c instanceof OptionButton) {
                ((OptionButton) c).setSelected(false);
            }
        }
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        if(name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif")) {
        	return true;
        }
        return false;
    }
    
    private boolean isFile(File file) {
    	String name = file.getName().toLowerCase();
    	if(name.endsWith(".doc") || name.endsWith(".docx") || name.endsWith(".pdf") || name.endsWith(".xlsx") || name.endsWith(".txt") || name.endsWith(".pptx")) {
    		return true;
    	}
    	return false;
    }
}

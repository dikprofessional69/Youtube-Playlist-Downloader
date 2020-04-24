package gui;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.openqa.selenium.WebDriverException;

import java.util.List;



import desciption.How;
import downloader.Download;
import globals.Global;


public class GUI implements ActionListener {

	JMenuBar mb;
	JMenu menu;
	JMenuItem i1,i2;
	
	JButton jButton;
	public JTextField jTextField;
	
	List<String> songnames;
	Download dl;
	How inforHow;
	
	JLabel label;
	ImageIcon icon;
	
	
	public GUI() throws ClassNotFoundException{
		
		mb=new JMenuBar();
		menu=new JMenu("Location Path");  
		i1=new JMenuItem("Driver Folder");  
		i2=new JMenuItem("Download Folder");  
		menu.add(i1);
		menu.add(i2);
		mb.add(menu);
		
		
		jButton=new JButton("Download Playlist");
		jTextField=new JTextField();
		
		
		URL imgpath=getClass().getResource("/robot.png");
	    
	  		
		icon =new ImageIcon(new ImageIcon(imgpath).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		label=new JLabel(icon);
		
		label.setBounds(150,80,250,250);
		//Custom class -Download Class object
		dl=new Download();
		inforHow=new How();
		
		Global.jFrame.add(label);
		
		Global.jFrame.setSize(600,600);
		jTextField.setBounds(40,280,500, 40);
		jButton.setBounds(210,330,140, 30);
		Global.jFrame.setTitle("Youtube Playlist Script");
		Global.jFrame.add(jTextField);
		Global.jFrame.add(jButton);

		jButton.addActionListener(this);
		i1.addActionListener(this);
		i2.addActionListener(this);
		
		Global.jFrame.setJMenuBar(mb);
		Global.jFrame.setLayout(null);
		Global.jFrame.setVisible(true);
		Global.jFrame.setResizable(false);
	}

	public static void main(String args[]) {
		try {
			new GUI();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource()==jButton) {
			String linkString=jTextField.getText();
			if(linkString.isBlank()) {
				JOptionPane.showMessageDialog(Global.jFrame,"Please Input a Playlist","Alert",JOptionPane.WARNING_MESSAGE); 
				
			}else {
				try {
				songnames= dl.openPlayListSite(linkString);//if empty can throw exception		
				}catch (WebDriverException e) {
					JOptionPane.showMessageDialog(Global.jFrame,"Looks like Playlist Couldnt Be Found , Close Chrome Tab and Input Proper link","Alert",JOptionPane.WARNING_MESSAGE); 
					
				}
				
				System.out.println(dl.urls);
				for(String url:dl.urls) {
					dl.singleSong(url);
				}	
			}
			
		
		}
		if(event.getSource()==i1) {
			  
				String tempdriverpath=(JOptionPane.showInputDialog(Global.jFrame,"Enter Location of Driver:")).trim();  
			   
			   FileOutputStream f=null;
			   BufferedOutputStream b=null;
			
				try {
					f=new FileOutputStream("driverlocation.txt");
					b=new BufferedOutputStream(f);
					byte byt[]=tempdriverpath.getBytes();
					b.write(byt);	
					
					b.close();
					f.close();
				}
					catch(Exception e) {
								System.out.println(e.getMessage());
							}
			   	
		
		}
		
		if(event.getSource()==i2) {
			   
			String tempdownloadpath=(JOptionPane.showInputDialog(Global.jFrame,"Enter Path to Download:")).trim();  
			   
			   FileOutputStream f=null;
			   BufferedOutputStream b=null;
			
				try {
					f=new FileOutputStream("downloadlocation.txt");
					b=new BufferedOutputStream(f);
					byte byt[]=tempdownloadpath.getBytes();
					b.write(byt);	
					
					b.close();
					f.close();
				}
					catch(Exception e) {
								System.out.println(e.getMessage());
							}
			   	
		
		}
	}
}

package com.mangni.vegaplan.mainframe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mangni.vegaplan.jettyserver.GamesServer;
import com.mangni.vegaplan.jobscheduling.MainScheduler;
import com.mangni.vegaplan.servletsrc.beforeplay.UserLogin;
import com.mangni.vegaplan.servletsrc.customsystem.tools.Initialise;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;
import com.mangni.vegaplan.toolshelper.XmlHelper;
import com.mangni.vegaplan.wzh.Http.Test;

public class MainFrame extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainFrame() throws HeadlessException, UnsupportedEncodingException
	{
		System.out.println("MainFrame.MainFrame()");
		JPanel mainpanel,buttonpanel,textpanel,layoutpanel;
		JButton buttonopen,buttonclose,buttonexit,buttonreload,buttonclear,buttontest;
		
		JFrame mainframe = new JFrame(Bean.getServername()+":游戏服务器"+Bean.getPort()+"端口");
        mainpanel = new JPanel();
        buttonpanel=new JPanel();
        textpanel=new JPanel();
        layoutpanel=new JPanel();
        
        buttonopen=new JButton("open");
        buttonopen.addActionListener(this);
        buttonopen.setActionCommand("open");
        buttonclose=new JButton("close");
        buttonclose.addActionListener(this);
        buttonclose.setActionCommand("close");
        buttonexit=new JButton("exit");
        buttonexit.addActionListener(this);
        buttonexit.setActionCommand("exit");
        buttonclear=new JButton("clear");
        buttonclear.addActionListener(this);
        buttonclear.setActionCommand("clear");
        
        buttonreload=new JButton("reload");
        buttonreload.addActionListener(this);
        buttonreload.setActionCommand("reload");
        
        buttontest=new JButton("test");
        buttontest.addActionListener(this);
        buttontest.setActionCommand("test");
        
        mainpanel.setLayout(new BorderLayout()); 
        buttonpanel.setLayout(new GridLayout(2,3,10,10));
        textpanel.setLayout(new GridLayout());
        layoutpanel.setLayout(new BorderLayout());
        
        Bean.getTextArea().setEditable(false);
        Bean.getTextArea().setBackground(Color.BLACK);
        Bean.getTextArea().setForeground(Color.WHITE);
        Bean.getTextArea().setText("游戏服务器程序"); 

        textpanel.add(new JScrollPane(Bean.getTextArea()));
   
        buttonpanel.add(buttonopen);	buttonpanel.add(buttonclose);	buttonpanel.add(buttonexit);	buttonpanel.add(buttonreload);
        buttonpanel.add(buttonclear);	buttonpanel.add(buttontest);
        layoutpanel.add(BorderLayout.NORTH,buttonpanel);
        layoutpanel.add(BorderLayout.SOUTH,Bean.getAllplayersum());
  
        mainpanel.add(textpanel); 
        mainpanel.add(layoutpanel,BorderLayout.EAST); 
        
        mainframe.add(mainpanel);  
        
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小对象  
        Dimension frameSize = mainframe.getSize();             // 获得窗口大小对象 
        
        mainframe.setSize(displaySize.width/2,displaySize.height/2);  
        mainframe.setLocation((displaySize.width - frameSize.width) / 4,(displaySize.height - frameSize.height) / 4); // 设置窗口居中显示器显示  
        mainframe.setDefaultCloseOperation(0);
        mainframe.setVisible(true); 
       
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		System.out.println("MainFrame.actionPerformed()");
		Date nowdate=new Date();		
		String time=Bean.getDateFormat().format(nowdate.getTime());
		if(e.getActionCommand().equals("open"))
		{
			if(Bean.getServer().isStarted())
			{	
				Bean.getTextArea().append("\r\n");
				Bean.getTextArea().append(time+"：游戏服务器正在运行...");
				Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
			}
			else
			{
				if(isPortUsing(Bean.getPort()))
				{
					JOptionPane.showMessageDialog(this,"端口"+Bean.getPort()+"被占用");
				}
				else
				{
					new XmlHelper();
					new MainScheduler();
					new UserLogin().loadGang();
					GamesServer gs=new GamesServer();
					Thread serverthread=new Thread(gs);
					serverthread.start();
					try
					{
						synchronized (gs)
						{
							gs.wait();
						}
						
					} catch (InterruptedException e1) {
						Bean.getTextArea().append("\r\n");
						Bean.getTextArea().append(time+"：错误："+e1.getMessage());
						Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
						e1.printStackTrace();
					}
					SendHttpRequest.sendrequest("updateonlinecount", "serverid="+Bean.getServerid()+"&onlinecount=  0");
					Bean.getTextArea().append("\r\n");
					Bean.getTextArea().append(time+"：游戏服务器启动...");
					Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
				}
			}
		}
		else if(e.getActionCommand().equals("close"))
		{
			if(Bean.getServer().isStarted())
			{
				try 
				{
					
					MainScheduler.getSched().resumeAll();
					MainScheduler.getSched().shutdown();
					SendHttpRequest.sendrequest("updateonlinecount", "serverid="+Bean.getServerid()+"&onlinecount=-1");
					Bean.getServer().stop();
					Bean.getTextArea().append("\r\n");
					Bean.getTextArea().append(time+"：游戏服务器关闭");
					Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				Bean.getTextArea().append("\r\n");
				Bean.getTextArea().append(time+"：游戏服务器已经关闭");
				Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
			}
		}
		else if(e.getActionCommand().equals("exit"))
		{
			if(Bean.getServer().isStarted())
			{ 
				try {
					SendHttpRequest.sendrequest("updateonlinecount", "serverid="+Bean.getServerid()+"&onlinecount=-1");
					Bean.getExecutor().shutdown();
					Bean.getServer().stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.exit(0);
		}
		else if(e.getActionCommand().equals("reload"))
		{
			new XmlHelper();
		}
		else if(e.getActionCommand().equals("clear"))
		{
			Bean.getTextArea().setText("");
		}
		else if(e.getActionCommand().equals("test"))
		{
			new Test();
		}
	}
	@SuppressWarnings("resource")
	public static boolean isPortUsing(int port)
	{  
		boolean flag = false;
		try 
		{
			new Socket("127.0.0.1",port);  
			flag = true;
		} catch (Exception e) {

		}
		return flag;  
	}  
	public static void main(String [] args)
	{
		System.out.println("MainFrame.main()");
		try {
			new Bean();
			//new SqlHelper();
			new Initialise();
			new MainFrame();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package ������;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Calculator extends JFrame {
	private JPanel screen;
	private JPanel key;
	private Stack<StringBuffer> store = new Stack<StringBuffer>();
	private StringBuffer temp = new StringBuffer("0");
	private StringBuffer symbol = new StringBuffer();
	private int count = 0;
	private JTextField showScreen;
	private JTextField symbShow;
	private JPopupMenu popupMenu;
	
	public Calculator() {
		this.setLayout(null);
		this.setTitle("������");
		this.setSize(1216, 1000);
		this.setLocation(200, 30);
		
		screen = new JPanel();	//������ʾ���
		key = new JPanel();		//�����������
		
		this.add(screen);
		this.add(key);
		
		screen.setBounds(0, 0, 876, 320);	//������ʾ����λ�úʹ�С
		screen.setBackground(new Color(190,190, 190));	//������ʾ���ı���ɫ
		screen.setLayout(null);				//������ʾ���Ϊ�޲���
		key.setBounds(0, 320, 876, 650);	//���ð�������λ�úʹ�С
		key.setBackground(new Color(242, 242, 242));		//���ð������ı���ɫ
		key.setLayout(new GridLayout(5, 5));
		
		showScreen = new JTextField(16);	//������ʾ�ı���
		showScreen.setEditable(false); 		//������ʾ�ı��򲻿ɱ༭
		symbShow = new JTextField(1);		//���������ı���
		symbShow.setEditable(false);		//���÷����ı��򲻿ɱ༭
		
		screen.add(showScreen);				//���ı�����ӵ���ʾ�����
		showScreen.setBounds(70, 130, 806, 70);	//�����ı����λ�úʹ�С
		showScreen.setBackground(new Color(190, 190, 190));	//�����ı���ı���ɫ
		showScreen.setFont(new Font("����", Font.BOLD, 80));//�����ı��������
		showScreen.setHorizontalAlignment(SwingConstants.RIGHT);//�����ı�����Ҷ���
		showScreen.setText(temp.toString());
		
		screen.add(symbShow);					//�������ı�����ӵ���ʾ�����
		symbShow.setBounds(0, 130, 70, 70);		//���÷����ı����λ�úʹ�С
		symbShow.setBackground(new Color(190, 190, 190));		//���÷����ı���ı���ɫ
		symbShow.setFont(new Font("����", Font.BOLD, 60));//���÷����ı��������
		symbShow.setHorizontalAlignment(SwingConstants.RIGHT);//���÷����ı�����Ҷ���
		symbShow.setText(symbol.toString());
		
		addComponent("...", key, new Color(215, 215, 215));			//��Ӱ���
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("C", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("��", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("7", key, new Color(242, 242, 242));
		addComponent("8", key, new Color(242, 242, 242));
		addComponent("9", key, new Color(242, 242, 242));
		addComponent("��", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("4", key, new Color(242, 242, 242));
		addComponent("5", key, new Color(242, 242, 242));
		addComponent("6", key, new Color(242, 242, 242));
		addComponent("-", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("1", key, new Color(242, 242, 242));
		addComponent("2", key, new Color(242, 242, 242));
		addComponent("3", key, new Color(242, 242, 242));
		addComponent("+", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("0", key, new Color(242, 242, 242));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("=", key, new Color(215, 215, 215));
		
		popupMenu = new JPopupMenu();	//	��������ʽ�˵�
		popupMenu.setBackground(new Color(224, 224, 224));
		JMenuItem copyResult = new JMenuItem("����");	//����JMenuItem�˵���
		copyResult.setFont(new Font("����", Font.PLAIN, 20));
		copyResult.setBackground(new Color(224, 224, 224));
		
		//ΪcopyResult�˵�������¼�������
		copyResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String $temp = showScreen.getText();
				Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
		        Transferable tText = new StringSelection($temp);  
		        clip.setContents(tText, null);  
			}
		});
		
		popupMenu.add(copyResult);	//ΪJPopupMenu�˵���Ӳ˵���
		//Ϊscreen������clicked����¼�������
		screen.addMouseListener(new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3)
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		copyResult.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
			{
				copyResult.setBackground(new Color(202, 202, 202));
			}
			
			public void mouseExited(MouseEvent e)
			{
				copyResult.setBackground(new Color(224, 224, 224));
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void addComponent(String name, JPanel layout, Color arg0)	//��Ӱ�������
	{
		JButton bt = new JButton(name);
		bt.setFont(new Font("����", Font.PLAIN, 50));
		bt.setBackground(arg0);
		layout.add(bt);
		bt.addActionListener(new ActionListener() {			//��Ӷ���������
			public void actionPerformed(ActionEvent e) {
				
				if(temp.toString().equals("0") || temp.toString().equals("��������Ϊ��"))
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					count = 0;
				}
				
				back(name);
				showScreen.setText(temp.toString());
				symbShow.setText(symbol.toString());
			}
		});
		bt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
			{
				bt.setBackground(new Color(184, 184, 184));
			}
			
			public void mouseExited(MouseEvent e)
			{
				bt.setBackground(arg0);
			}
		});
	}
	
	private void back(String text)	//��̨������
	{
		switch(text)	//�ж������������
		{
		case "0":
			if(count<=15)
			{
				temp.append("0");
				count++;
			}
			break;
		case "1":
			if(count<=15)
			{
				temp.append("1");
				count++;
			}
			break;
		case "2":
			if(count<=15)
			{
				temp.append("2");
				count++;
			}
			break;
		case "3":
			if(count<=15)
			{
				temp.append("3");
				count++;
			}
			break;
		case "4":
			if(count<=15)
			{
				temp.append("4");
				count++;
			}
			break;
		case "5":
			if(count<=15)
			{
				temp.append("5");
				count++;
			}
			break;
		case "6":
			if(count<=15)
			{
				temp.append("6");
				count++;
			}
			break;
		case "7":
			if(count<=15)
			{
				temp.append("7");
				count++;
			}
			break;
		case "8":
			if(count<=15)
			{
				temp.append("8");
				count++;
			}
			break;
		case "9":
			if(count<=15)
			{
				temp.append("9");
				count++;
			}
			break;
		case "+":
			add();
			break;
		case "-":
			sub();
			break;
		case "��":
			mult();
			break;
		case "��":
			divi();
			break;
		case "=":
			calcu();
			break;
		case "C":
			clear();
			break;
		case "...":
			break;
		}
	}
	
	private void add()	//�ӷ�����
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			symbol.append("+");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("+");
		}
		
		if(store.empty())							//�ж��Ƿ�Ϊ��ջ
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("+"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("��"))	//�����˺�
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("+"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("��"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//��������ֵ
				Num2 = Integer.parseInt(temp.toString());	//������ֵ
				if(Num2 == 0)								//������Ϊ��
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//��ջ���
					{
						store.pop();
					}
					temp.append("��������Ϊ�� ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("+"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("+"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void sub()	//��������
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			symbol.append("-");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("-");
		}
		
		if(store.empty())							//�ж��Ƿ�Ϊ��ջ
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("-"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("��"))	//�����˺�
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("-"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("��"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//��������ֵ
				Num2 = Integer.parseInt(temp.toString());	//������ֵ
				if(Num2 == 0)								//������Ϊ��
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//��ջ���
					{
						store.pop();
					}
					temp.append("��������Ϊ�� ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("-"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("-"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void mult()	//�˷�����
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			symbol.append("��");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("��");
		}
		
		if(store.empty())							//�ж��Ƿ�Ϊ��ջ
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("��"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("��"))	//�����˺�
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("��"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("��"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//��������ֵ
				Num2 = Integer.parseInt(temp.toString());	//������ֵ
				if(Num2 == 0)								//������Ϊ��
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//��ջ���
					{
						store.pop();
					}
					temp.append("��������Ϊ�� ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("��"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("��"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void divi()	//��������
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			symbol.append("��");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("��");
		}
		
		if(store.empty())							//�ж��Ƿ�Ϊ��ջ
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("��"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("��"))	//�����˺�
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("��"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("��"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//��������ֵ
				Num2 = Integer.parseInt(temp.toString());	//������ֵ
				if(Num2 == 0)								//������Ϊ��
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//��ջ���
					{
						store.pop();
					}
					temp.append("��������Ϊ�� ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("��"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("��"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void calcu()	//�Ⱥŷ���
	{
		StringBuffer $temp1 = new StringBuffer();
		StringBuffer $temp2 = new StringBuffer();
		StringBuffer $temp3 = new StringBuffer();
		int num1 = 0;
		int num2 = 0;
		
		if(symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			symbol.append("=");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("=");
		}
		
		if(store.empty())
		{
			if(temp.toString().equals("\0"))
			{
				temp.append("0");
			}
		}
		else
		{
			$temp1.append(temp);
			while(!store.empty())
			{
				$temp2.append(store.pop());
				$temp3.append(store.pop());
				if($temp2.toString().equals("+"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					num1 = num1 + num2;
					$temp1.delete(0, $temp1.length());
					$temp2.delete(0, $temp2.length());
					$temp3.delete(0, $temp3.length());
					$temp1.append(new StringBuffer(new String(new Integer(num1).toString())));
				}
				else if($temp2.toString().equals("-"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					num1 = num2 - num1;
					$temp1.delete(0, $temp1.length());
					$temp2.delete(0, $temp2.length());
					$temp3.delete(0, $temp3.length());
					$temp1.append(new StringBuffer(new String(new Integer(num1).toString())));
				}
				else if($temp2.toString().equals("��"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					num1 = num2 * num1;
					$temp1.delete(0, $temp1.length());
					$temp2.delete(0, $temp2.length());
					$temp3.delete(0, $temp3.length());
					$temp1.append(new StringBuffer(new String(new Integer(num1).toString())));
				}
				else if($temp2.toString().equals("��"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					if(num1 == 0) 
					{
						int sb_length = temp.length();
						temp.delete(0, sb_length);
						while(!store.empty())					//��ջ���
						{
							store.pop();
						}
						temp.append("��������Ϊ�� ");
					}
					else
					{
						num1 = num2 / num1;
					}
					$temp1.delete(0, $temp1.length());
					$temp2.delete(0, $temp2.length());
					$temp3.delete(0, $temp3.length());
					$temp1.append(new StringBuffer(new String(new Integer(num1).toString())));
				}
			}
		}
		int sb_length = temp.length();
		temp.delete(0, sb_length);
		temp.append($temp1);
	}
	
	private void clear()	//C������
	{
		count = 0;									//����������
		
		if(!symbol.toString().equals("\0"))	//������λ�Ƿ�Ϊ��
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append(" ");
		}
		
		int sb_length = temp.length();
		temp.delete(0, sb_length);
		while(!store.empty())						//��ջ���
		{
			store.pop();
		}
		temp.append("0");
	}
	
	public static void main(String[] args) {
		new Calculator();
	}
}

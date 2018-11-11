package 计算器;
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
		this.setTitle("计算器");
		this.setSize(1216, 1000);
		this.setLocation(200, 30);
		
		screen = new JPanel();	//创建显示面板
		key = new JPanel();		//创建按键面板
		
		this.add(screen);
		this.add(key);
		
		screen.setBounds(0, 0, 876, 320);	//设置显示面板的位置和大小
		screen.setBackground(new Color(190,190, 190));	//设置显示面板的背景色
		screen.setLayout(null);				//设置显示面板为无布局
		key.setBounds(0, 320, 876, 650);	//设置按键面板的位置和大小
		key.setBackground(new Color(242, 242, 242));		//设置按键面板的背景色
		key.setLayout(new GridLayout(5, 5));
		
		showScreen = new JTextField(16);	//创建显示文本框
		showScreen.setEditable(false); 		//设置显示文本框不可编辑
		symbShow = new JTextField(1);		//创建符号文本框
		symbShow.setEditable(false);		//设置符号文本框不可编辑
		
		screen.add(showScreen);				//将文本框添加到显示面板中
		showScreen.setBounds(70, 130, 806, 70);	//设置文本框的位置和大小
		showScreen.setBackground(new Color(190, 190, 190));	//设置文本框的背景色
		showScreen.setFont(new Font("黑体", Font.BOLD, 80));//设置文本框的字体
		showScreen.setHorizontalAlignment(SwingConstants.RIGHT);//设置文本框的右对齐
		showScreen.setText(temp.toString());
		
		screen.add(symbShow);					//将符号文本框添加到显示面板中
		symbShow.setBounds(0, 130, 70, 70);		//设置符号文本框的位置和大小
		symbShow.setBackground(new Color(190, 190, 190));		//设置符号文本框的背景色
		symbShow.setFont(new Font("黑体", Font.BOLD, 60));//设置符号文本框的字体
		symbShow.setHorizontalAlignment(SwingConstants.RIGHT);//设置符号文本框的右对齐
		symbShow.setText(symbol.toString());
		
		addComponent("...", key, new Color(215, 215, 215));			//添加按键
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("C", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("÷", key, new Color(215, 215, 215));
		addComponent("...", key, new Color(215, 215, 215));
		addComponent("7", key, new Color(242, 242, 242));
		addComponent("8", key, new Color(242, 242, 242));
		addComponent("9", key, new Color(242, 242, 242));
		addComponent("×", key, new Color(215, 215, 215));
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
		
		popupMenu = new JPopupMenu();	//	创建弹出式菜单
		popupMenu.setBackground(new Color(224, 224, 224));
		JMenuItem copyResult = new JMenuItem("复制");	//创建JMenuItem菜单项
		copyResult.setFont(new Font("宋体", Font.PLAIN, 20));
		copyResult.setBackground(new Color(224, 224, 224));
		
		//为copyResult菜单项添加事件监听器
		copyResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String $temp = showScreen.getText();
				Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
		        Transferable tText = new StringSelection($temp);  
		        clip.setContents(tText, null);  
			}
		});
		
		popupMenu.add(copyResult);	//为JPopupMenu菜单添加菜单项
		//为screen面板添加clicked鼠标事件监听器
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
	
	private void addComponent(String name, JPanel layout, Color arg0)	//添加按键方法
	{
		JButton bt = new JButton(name);
		bt.setFont(new Font("黑体", Font.PLAIN, 50));
		bt.setBackground(arg0);
		layout.add(bt);
		bt.addActionListener(new ActionListener() {			//添加动作监听器
			public void actionPerformed(ActionEvent e) {
				
				if(temp.toString().equals("0") || temp.toString().equals("除数不能为零"))
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
	
	private void back(String text)	//后台处理方法
	{
		switch(text)	//判断输入进来的数
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
		case "×":
			mult();
			break;
		case "÷":
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
	
	private void add()	//加法方法
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//检查符号位是否为空
		{
			symbol.append("+");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("+");
		}
		
		if(store.empty())							//判断是否为空栈
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
			if($temp.toString().equals("×"))	//遇到乘号
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
			else if($temp.toString().equals("÷"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//被除数赋值
				Num2 = Integer.parseInt(temp.toString());	//除数赋值
				if(Num2 == 0)								//当除数为零
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//将栈清空
					{
						store.pop();
					}
					temp.append("除数不能为零 ");
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
	
	private void sub()	//减法方法
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//检查符号位是否为空
		{
			symbol.append("-");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("-");
		}
		
		if(store.empty())							//判断是否为空栈
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
			if($temp.toString().equals("×"))	//遇到乘号
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
			else if($temp.toString().equals("÷"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//被除数赋值
				Num2 = Integer.parseInt(temp.toString());	//除数赋值
				if(Num2 == 0)								//当除数为零
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//将栈清空
					{
						store.pop();
					}
					temp.append("除数不能为零 ");
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
	
	private void mult()	//乘法方法
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//检查符号位是否为空
		{
			symbol.append("×");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("×");
		}
		
		if(store.empty())							//判断是否为空栈
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("×"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("×"))	//遇到乘号
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("×"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("÷"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//被除数赋值
				Num2 = Integer.parseInt(temp.toString());	//除数赋值
				if(Num2 == 0)								//当除数为零
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//将栈清空
					{
						store.pop();
					}
					temp.append("除数不能为零 ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("×"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("×"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void divi()	//除法方法
	{
		StringBuffer $temp = new StringBuffer();
		StringBuffer $temp1 = new StringBuffer();
		int Num1 = 0;
		int Num2 = 0;
		int result = 0;
		
		if(symbol.toString().equals("\0"))	//检查符号位是否为空
		{
			symbol.append("÷");
		}
		else
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append("÷");
		}
		
		if(store.empty())							//判断是否为空栈
		{
			store.push(new StringBuffer(temp));
			store.push(new StringBuffer("÷"));
			int sb_length = temp.length();
			temp.delete(0, sb_length);
			temp.append("0");
		}
		else
		{
			$temp.append(store.pop());
			if($temp.toString().equals("×"))	//遇到乘号
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());
				Num2 = Integer.parseInt(temp.toString());
				result = Num1 * Num2;
				StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
				store.push(new StringBuffer(sbResult));
				store.push(new StringBuffer("÷"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
			else if($temp.toString().equals("÷"))
			{
				$temp1.append(store.pop());
				Num1 = Integer.parseInt($temp1.toString());	//被除数赋值
				Num2 = Integer.parseInt(temp.toString());	//除数赋值
				if(Num2 == 0)								//当除数为零
				{
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					while(!store.empty())					//将栈清空
					{
						store.pop();
					}
					temp.append("除数不能为零 ");
				}
				else
				{
					result = Num1 / Num2;
					StringBuffer sbResult = new StringBuffer(new String(new Integer(result).toString()));
					store.push(new StringBuffer(sbResult));
					store.push(new StringBuffer("÷"));
					int sb_length = temp.length();
					temp.delete(0, sb_length);
					temp.append("0");
				}
			}
			else if($temp.toString().equals("+") || $temp.toString().equals("-"))
			{
				store.push(new StringBuffer($temp));
				store.push(new StringBuffer(temp));
				store.push(new StringBuffer("÷"));
				int sb_length = temp.length();
				temp.delete(0, sb_length);
				temp.append("0");
			}
		}
	}
	
	private void calcu()	//等号方法
	{
		StringBuffer $temp1 = new StringBuffer();
		StringBuffer $temp2 = new StringBuffer();
		StringBuffer $temp3 = new StringBuffer();
		int num1 = 0;
		int num2 = 0;
		
		if(symbol.toString().equals("\0"))	//检查符号位是否为空
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
				else if($temp2.toString().equals("×"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					num1 = num2 * num1;
					$temp1.delete(0, $temp1.length());
					$temp2.delete(0, $temp2.length());
					$temp3.delete(0, $temp3.length());
					$temp1.append(new StringBuffer(new String(new Integer(num1).toString())));
				}
				else if($temp2.toString().equals("÷"))
				{
					num1 = Integer.parseInt($temp1.toString());
					num2 = Integer.parseInt($temp3.toString());
					if(num1 == 0) 
					{
						int sb_length = temp.length();
						temp.delete(0, sb_length);
						while(!store.empty())					//将栈清空
						{
							store.pop();
						}
						temp.append("除数不能为零 ");
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
	
	private void clear()	//C键方法
	{
		count = 0;									//计数器清零
		
		if(!symbol.toString().equals("\0"))	//检查符号位是否为空
		{
			int sb_length = symbol.length();
			symbol.delete(0, sb_length);
			symbol.append(" ");
		}
		
		int sb_length = temp.length();
		temp.delete(0, sb_length);
		while(!store.empty())						//将栈清空
		{
			store.pop();
		}
		temp.append("0");
	}
	
	public static void main(String[] args) {
		new Calculator();
	}
}

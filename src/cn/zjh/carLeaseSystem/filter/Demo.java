package cn.zjh.carLeaseSystem.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true){
			
		
		System.out.println("请问要进行哪种计算？");
		System.out.println("1.值计算       2.分数计算");
		int choose = sc.nextInt();
		if(choose == 1){
			//输入表达式
			System.out.println("请输入表达式");
			String str = sc.next();	
			//化简表达式huajian();
			String s = huajian(str);
			String num = "";//记录数字
			//定义一个栈，里面存放计算符号+，-，*,（，）
			Stack<Character> charStack = new Stack<Character>();	
			//获取表达式长度
			int len = s.length();
			//定义list集合，用来存储表达式中的各个字符
			List<String> list = new ArrayList<String>();
			for(int i=0;i<len;i++){
				char ch = s.charAt(i);
				if(isAllChar(ch)){	
					if(num!=""){
						list.add(num);
						num="";
					}
					if(ch=='(')
					{
						//将(压入字符栈中
						charStack.push(ch);						
					}else if(isChar(ch))
					{
						//获取栈顶符号
						char top = charStack.peek();
						//比较字符和栈顶的计算符号哪个优先级大
						if(isHigh(ch, top)){
							
							// ch优先级大于top 压栈
							charStack.push(ch);
						}else{		
							//判断是否为空栈
							while(true){	
								char t=charStack.peek();
								if(t=='(')
									break;							
								if(isHigh(ch, t))
									break;
								//把结果添加到list集合中
								list.add(Character.toString(t));
								t=charStack.pop();
							}
							charStack.push(ch);
						}
		
					}else if(ch==')'){
						char t = charStack.pop();				
						while(t!='('&&!charStack.isEmpty())
						{
							list.add(Character.toString(t));
							t = charStack.pop();
						}	
					}

				}else{
					num+=ch;
				}
			}
			//计算后缀表达式	
			//定义一个栈，栈中保存每步计算结果
			Stack<Float> numStack = new Stack<Float>();
			int size = list.size();
			for(int i=0;i<size;i++){
				String t =list.get(i);
				if(isNumber(t)){	
					//结果压入栈中
					numStack.push(Float.parseFloat(t));
				}else{
					char c = t.charAt(0);
					float b = numStack.pop();
					//判断栈是否为空
					float a = numStack.pop();					
						switch(c)
						{
							case '+':
								//压栈
								numStack.push(a+b);
								break;
							case '-':
								numStack.push(a-b);
								break;
							case '*':
								numStack.push(a*b);
								break;
							case '/':
								numStack.push(a/b);
								break;
							default:
								break;		
						}
				}
			}
			System.out.print("?");
			float myResult = sc.nextFloat();
			float result = numStack.pop();
			if(result == myResult){
				System.out.println("答对了，你真是个天才");
			}else{
				System.out.println("再想想吧，答案似乎是"+result+"哦！");
			}
		}else if(choose == 2){
			System.out.println("请输入第一个分数(格式");
			String data1 = sc.next();
			System.out.println("请输入运算符(+-*/)");
			String operation = sc.next();
			System.out.println("请输入第二个分数");
			String data2 = sc.next();
				
			// 根据用户输入进行具体运算
			Calculator cal = new Calculator();
			System.out.println("运算结果为:");
			cal.compute(data1, operation, data2);			
		}else{
			System.out.println("系统结束！");
		}
		}
	}
	
	//判断是否匹配数字的正则，如果匹配返回true；
	 public static boolean isNumber(String str){
		 //使用正则表达式
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
 }
	//获取符号的优先级，先乘除后加减
	public static int youxianji(char a){
		if(a=='+')
			return 0;
		else if(a=='-')
			return 1;
		else if(a=='*')			
			return 3;
		else if(a=='/')
			return 4;
		else 
			return -1;
	}
		//化简表达式	
	public static String huajian(String str){
		//利用正则表达式进行替换
		String s = str.replaceAll("(?<![0-9)}\\]])(?=-[0-9({\\[])", "0");	
		//将表达式中的 {}[]替换为()
		s = s.replace('[', '(');
		s = s.replace('{', '(');
		s = s.replace(']', ')');
		s = s.replace(']', ')');
		//添加括号方便计算
		s="("+s+")";
		return s ;
	}
	//判断是否为计算字符，如果是，返回true，否则返回false
	public static boolean isChar(char c){
		if(c=='+'||c=='-'||c=='*'||c=='/')
			return true;
		else		
			return false;
	}
	//判断是否为字符，包含()括号
	public static boolean isAllChar(char c){
		if(c=='+'||c=='-'||c=='*'||c=='/')
			return true;
		else if(c=='('||c==')')
			return true;
		else		
			return false;
	}
	//判断两个计算符号的优先级，如果第一个大于第二个，则返回true；
	public static boolean isHigh(char a,char b){	
			int a1 = youxianji(a);
			int b1 = youxianji(b);
			if(a1>b1)
				return true;
			else
				return false;
	}
}
//分数计算类
class Calculator {
	int numerator;  // 分子
	int denominator; // 分母
	
	Calculator(){
	}

	Calculator(int a,int b){
		if(a == 0){
			numerator = 0;
			denominator = 1;
		}
		else{
			setNumeratorAndDenominator(a,b);
		}
	}
	
	void setNumeratorAndDenominator(int a, int b){  // 设置分子和分母
		int c = f(Math.abs(a),Math.abs(b));         // 计算最大公约数
		numerator = a / c;
		denominator = b / c;
		if(numerator<0 && denominator<0){
			numerator = - numerator;
			denominator = - denominator;
		}
	}
	
	int getNumerator(){
		return numerator;
	}

	int getDenominator(){
		return denominator;
	}
	
	int f(int a,int b){  // 求a和b的最大公约数
		if(a < b){
			int c = a;
			a = b;
			b = c;
		}
		int r = a % b;
		while(r != 0){
			a = b;
			b = r;;
			r = a % b;
		}
		return b;
	}
	
	Calculator add(Calculator r){  // 加法运算
		int a = r.getNumerator();
		int b = r.getDenominator();
		int newNumerator = numerator * b + denominator * a;
		int newDenominator = denominator * b;
		Calculator result = new Calculator(newNumerator,newDenominator);
		return result;
	}
	
	Calculator sub(Calculator r){  // 减法运算
		int a = r.getNumerator();
		int b = r.getDenominator();
		int newNumerator = numerator * b - denominator * a;
		int newDenominator = denominator * b;
		Calculator result = new Calculator(newNumerator,newDenominator);
		return result;
	} 
	
	Calculator muti(Calculator r){ // 乘法运算
		int a = r.getNumerator();
		int b = r.getDenominator();
		int newNumerator = numerator * a;
		int newDenominator = denominator * b;
		Calculator result = new Calculator(newNumerator,newDenominator);
		return result;
	}
	
	Calculator div(Calculator r){  // 除法运算
		int a = r.getNumerator();
		int b = r.getDenominator();
		int newNumerator = numerator * b;
		int newDenominator = denominator * a;
		Calculator result = new Calculator(newNumerator,newDenominator);
		return result;
	}
	
	// 封装了具体运算，主要为对输入进行转换，对输出封装
	public static void compute(String data1,String operation,String data2){
		StringTokenizer fenxi = new StringTokenizer(data1,"/");
	    int data1_1 = Integer.parseInt(fenxi.nextToken());
	    int data1_2 = Integer.parseInt(fenxi.nextToken());
		fenxi = new StringTokenizer(data2,"/");
	    int data2_1 = Integer.parseInt(fenxi.nextToken());
	    int data2_2 = Integer.parseInt(fenxi.nextToken());
	    	    
		Calculator r1 = new Calculator(data1_1,data1_2);
		Calculator r2 = new Calculator(data2_1,data2_2);
		
		Calculator result;
		int a,b;
		if(operation.equals("+")){
 			result = r1.add(r2);
			a = result.getNumerator();
 			b = result.getDenominator();
			System.out.println(data1+" "+operation+" " +data2+" = " + a + "/" + b);
		}
		
		if(operation.equals("-")){
 			result = r1.sub(r2);
			a = result.getNumerator();
 			b = result.getDenominator();
			System.out.println(data1+" "+operation+" " +data2+" = " + a + "/" + b);
		}
		
		if(operation.equals("*")){
 			result = r1.muti(r2);
			a = result.getNumerator();
 			b = result.getDenominator();
			System.out.println(data1+" "+operation+" " +data2+" = " + a + "/" + b);
		}
		
		if(operation.equals("/")){
 			result = r1.div(r2);
			a = result.getNumerator();
 			b = result.getDenominator();
			System.out.println(data1+" "+operation+" " +data2+" = " + a + "/" + b);
		}
	}
}

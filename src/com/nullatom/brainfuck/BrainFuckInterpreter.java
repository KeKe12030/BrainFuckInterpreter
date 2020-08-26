package com.nullatom.brainfuck;

import java.util.Scanner;

public class BrainFuckInterpreter {
	public static final char NEXT = '>';//右移
	public static final char PREVIOUS = '<';//左移 previous
	public static final char OUTPUT  = '.';//输出
	public static final char INPUT = ',';//输入
	public static final char PLUS = '+';//加
	public static final char MINUS = '-';//减
	public final static char BRACKET_LEFT = '[';//循环左括号
	public final static char BRACKET_RIGHT = ']';//循环右括号
	public static int[] container = null;
	
	public static int containerPointer = 0;
	public String code = "";
	public static char[] codes = null;
	
	public static Long startTime = System.currentTimeMillis();

//	public static StringBuilder debug = new StringBuilder();

	public BrainFuckInterpreter() {
		throw new RuntimeException("请勿直接使用空构造器构造");
	}
	public BrainFuckInterpreter(int len,String code) {
		// TODO Auto-generated constructor stub
		this.code = code;
		codes = code.toCharArray();
		container = new int[len];
	}

	public void start() {
		int codePointer = 0;//当前循环用来定位代码的计数器
		char codeChar = ' ';
		while(codePointer<codes.length){
			codeChar = codes[codePointer];
//			if(codeChar!='['&&codeChar!=']')
//				debug.append(codeChar);
//			System.out.println(debug.toString());
			switch(codeChar) {
			case NEXT://右移
				containerPointer ++;
				codePointer++;
				break;
			case PREVIOUS://左移
				containerPointer --;
				codePointer++;
				break;
			case BRACKET_LEFT://循环左括号 [

				//1.判断当前指针内容是否为空
				if(container[containerPointer]!=0) {
					//如果当前内容不是空
					//需要继续执行下面的指令
					codePointer++;
				}else {
					//如果当前内容是空
					int i = 1;
					while(i > 0) {
						//找到最近的一个 ]
						//先累计当前的 ] 所以要 ++codePointer
						char findCode = codes[++codePointer];
						if(findCode == BRACKET_RIGHT) {
							i--;
						}else if(findCode == BRACKET_LEFT) {
							i++;
						}
					}
					
				}
				break;
			case BRACKET_RIGHT://循环右括号 ]
				//需要找到对应的 [
				
				//判断当前指针内容是否为空
				if(container[containerPointer]!=0) {
					//如果当前内容不是空
					//需要继续执行上一个 [的指令
					int i = 1;
					while(i > 0) {
						//找到对应的 [
						char findCode = codes[--codePointer];
						if(findCode == BRACKET_LEFT) {//如果是 [ 
							i--;
						}else if(findCode == BRACKET_RIGHT) {//如果是 ] 
							i++;
						}
					}
					//如果找到了对应的 [ 需要 [-1 的位置，执行后面的指令
					codePointer++;
				}else {
					//如果内容是空，执行下面的命令
					codePointer++;
				}
				break;
			case MINUS://减
				container[containerPointer] --;
				codePointer++;
				break;
			case PLUS://加
				container[containerPointer]++;
				codePointer++;
				break;
			case INPUT://输入
				Long startInputTime = System.currentTimeMillis();
				System.out.print("输入到"+containerPointer+"：");
				container[containerPointer] = new Scanner(System.in).nextInt();
				codePointer++;
				startTime = startTime + (System.currentTimeMillis() - startInputTime);
				break;
			case OUTPUT://输出
				System.out.println((char)container[containerPointer]);
				codePointer++;
				break;
			default:
				codePointer++;
				break;
			}
		}
	}


	public static void main(String[] args) {
		if(args.length != 2) {
			throw new RuntimeException("参数格式错误！\n提示：第一个参数为：容器长度\n第二个参数为：代码 / 代码文件");
		}
		BrainFuckInterpreter bfi = new BrainFuckInterpreter(Integer.valueOf(args[0]),args[1]);
		bfi.start();
		System.out.println("============");
		System.out.println("BrainFuck运行总耗时："+(System.currentTimeMillis() - startTime)+"ms");
		System.out.println("容器中的内容：");
		int i = 0;
		for(int a : BrainFuckInterpreter.container) { System.out.println("["+i+"] -> "+a); i++;}
		System.out.println("============");

	}

}

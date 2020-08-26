package com.nullatom.brainfuck;

import java.util.Scanner;

public class BrainFuckInterpreter {
	public static final char NEXT = '>';//����
	public static final char PREVIOUS = '<';//���� previous
	public static final char OUTPUT  = '.';//���
	public static final char INPUT = ',';//����
	public static final char PLUS = '+';//��
	public static final char MINUS = '-';//��
	public final static char BRACKET_LEFT = '[';//ѭ��������
	public final static char BRACKET_RIGHT = ']';//ѭ��������
	public static int[] container = null;
	
	public static int containerPointer = 0;
	public String code = "";
	public static char[] codes = null;
	
	public static Long startTime = System.currentTimeMillis();

//	public static StringBuilder debug = new StringBuilder();

	public BrainFuckInterpreter() {
		throw new RuntimeException("����ֱ��ʹ�ÿչ���������");
	}
	public BrainFuckInterpreter(int len,String code) {
		// TODO Auto-generated constructor stub
		this.code = code;
		codes = code.toCharArray();
		container = new int[len];
	}

	public void start() {
		int codePointer = 0;//��ǰѭ��������λ����ļ�����
		char codeChar = ' ';
		while(codePointer<codes.length){
			codeChar = codes[codePointer];
//			if(codeChar!='['&&codeChar!=']')
//				debug.append(codeChar);
//			System.out.println(debug.toString());
			switch(codeChar) {
			case NEXT://����
				containerPointer ++;
				codePointer++;
				break;
			case PREVIOUS://����
				containerPointer --;
				codePointer++;
				break;
			case BRACKET_LEFT://ѭ�������� [

				//1.�жϵ�ǰָ�������Ƿ�Ϊ��
				if(container[containerPointer]!=0) {
					//�����ǰ���ݲ��ǿ�
					//��Ҫ����ִ�������ָ��
					codePointer++;
				}else {
					//�����ǰ�����ǿ�
					int i = 1;
					while(i > 0) {
						//�ҵ������һ�� ]
						//���ۼƵ�ǰ�� ] ����Ҫ ++codePointer
						char findCode = codes[++codePointer];
						if(findCode == BRACKET_RIGHT) {
							i--;
						}else if(findCode == BRACKET_LEFT) {
							i++;
						}
					}
					
				}
				break;
			case BRACKET_RIGHT://ѭ�������� ]
				//��Ҫ�ҵ���Ӧ�� [
				
				//�жϵ�ǰָ�������Ƿ�Ϊ��
				if(container[containerPointer]!=0) {
					//�����ǰ���ݲ��ǿ�
					//��Ҫ����ִ����һ�� [��ָ��
					int i = 1;
					while(i > 0) {
						//�ҵ���Ӧ�� [
						char findCode = codes[--codePointer];
						if(findCode == BRACKET_LEFT) {//����� [ 
							i--;
						}else if(findCode == BRACKET_RIGHT) {//����� ] 
							i++;
						}
					}
					//����ҵ��˶�Ӧ�� [ ��Ҫ [-1 ��λ�ã�ִ�к����ָ��
					codePointer++;
				}else {
					//��������ǿգ�ִ�����������
					codePointer++;
				}
				break;
			case MINUS://��
				container[containerPointer] --;
				codePointer++;
				break;
			case PLUS://��
				container[containerPointer]++;
				codePointer++;
				break;
			case INPUT://����
				Long startInputTime = System.currentTimeMillis();
				System.out.print("���뵽"+containerPointer+"��");
				container[containerPointer] = new Scanner(System.in).nextInt();
				codePointer++;
				startTime = startTime + (System.currentTimeMillis() - startInputTime);
				break;
			case OUTPUT://���
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
			throw new RuntimeException("������ʽ����\n��ʾ����һ������Ϊ����������\n�ڶ�������Ϊ������ / �����ļ�");
		}
		BrainFuckInterpreter bfi = new BrainFuckInterpreter(Integer.valueOf(args[0]),args[1]);
		bfi.start();
		System.out.println("============");
		System.out.println("BrainFuck�����ܺ�ʱ��"+(System.currentTimeMillis() - startTime)+"ms");
		System.out.println("�����е����ݣ�");
		int i = 0;
		for(int a : BrainFuckInterpreter.container) { System.out.println("["+i+"] -> "+a); i++;}
		System.out.println("============");

	}

}

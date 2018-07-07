package com.mangni.vegaplan.wzh.Http;

import java.util.Random;  

public class CharacterUtils {  

	public static String getRandomString(int length){  
		String str="ABCDEFGHJKLMNPQRSTUVWXYZ23456789";  
		Random random = new Random();  
		StringBuffer sb = new StringBuffer();  

		for(int i = 0 ; i < length; ++i){  
			int number = random.nextInt(str.length());

			sb.append(str.charAt(number));
		}  
		return sb.toString();  
	}  


	public static void main(String[] args){  
		for(int i=0;i<300;i++){
			System.out.println(getRandomString(12));  
		}  
	}
}  

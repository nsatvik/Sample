package com.jinkchak;

import java.security.InvalidAlgorithmParameterException;

public class MainGUI {
	
	public MainGUI()
	{
		
	}
	
	public static void main(String args[])
	{
			Schmidt_Samoa_Encryptor ssEncryptor = new Schmidt_Samoa_Encryptor();
			int [] message = {100,12};
			int  res = ssEncryptor.encrypt(127);
//			for (int i=0; i<res.length; ++i)
//				System.out.print(res[i]);
			System.out.println();
			System.out.println("The cipher is "+res);
			System.out.println("The message is "+ ssEncryptor.decrypt(2337));
			ssEncryptor.Display();
//		int [] a = {1,2,3};
//		System.out.println(a[0]+""+a[1]+""+a[2]);
	}

}

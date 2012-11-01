package com.jinkchak;

import java.security.InvalidAlgorithmParameterException;

import org.eclipse.swt.widgets.Display;

/**
Schmidt-Samoa Cryptosystem
Key generation
•	Choose two large distinct primes p and q and compute N = p2q 
•	Compute d = N^-1 mod lcm(p – 1, q – 1)
•	Now N is the public key and d is the private key.

Encryption
To encrypt a message m we compute the cipher text as c = m^N mod N

Decryption
To decrypt a cipher text c we compute the plaintext as m = c^d mod pq which like for Rabin and RSA can be computed with the Chinese remainder theorem.
*/
public class Schmidt_Samoa_Encryptor {
	private int p, q;
	private int public_key, private_key;
	
	private static final int BLOCK_SIZE = 6;			//For splitting a String of text into blocks
	
	/*
	 * This constructor initializes p,q and other variables.
	 */
	public Schmidt_Samoa_Encryptor()
	{
		reInitialize(23, 31);	
	}
	/*
	 * Re-init the system with the new p and q
	 */
	public void reInitialize(int p, int q)
	{
		this.p = p;
		this.q = q;
		public_key = computeN();
		try {
			private_key = modular_Equation_Solver(public_key, 1, lcm(p-1, q-1));
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
	}
	/*
	 * Compute and return the value of N = p^2 * q.
	 */
	private int computeN()
	{
		return p*p*q;
	}
	/*
	 * Compute the lcm of a and b
	 */
	private int lcm(int a, int b)
	{
		return (a*b)/gcd(a,b);
	}
	
	/*
	 * Compute the gcd of a and b.
	 */
	private int gcd(int a, int b) {
		if (b==0)
			return a;
		return gcd(b,a%b);
	}
	
	/*
	 * Extended euclid's algorithm
	 */
	private int[] extendedEuclidsAlgo(int a, int b)
	{
		int []result = new int[3];
		if(b==0)
		{
			result[0] = a;// index 0 is x
			result[1] = 1;// index 1 is y
			result[2] = 0;// index 2 is d ... ax+by = d
			//System.out.println(result[0]+" "+result[1]+" "+result[2]);
			return result;
		}
		
		int []result_temp = extendedEuclidsAlgo(b, a%b);
		int []final_result = {result_temp[0],result_temp[2],result_temp[1]-(a/b)*result_temp[2]};
		//System.out.println(final_result[0]+" "+final_result[1]+" "+final_result[2]);
		return final_result;
	}
	
	/*
	 * Modular Equation solver.
	 */
	private int modular_Equation_Solver(int a, int b, int n) throws InvalidAlgorithmParameterException
	{		
		int []solution = extendedEuclidsAlgo(a, n);
		int d = solution[0];
		int x1 = solution[1];
		int y1 = solution[2];
		
		if(b%d == 0)
		{
			int value = x1*b/d;
			return correctValue(value, n);
		}
		else
			throw new InvalidAlgorithmParameterException();
	}
	
	private int correctValue(int value, int n) {
		while(value<0)
		{
			
			value += n;
		}
		System.out.println(value);
		return value%n;
	}
	
	
	public int modularExponentiator(int a, int b, int n)
	{
		int c = 0;
		int d = 1;
		String binaryB = Integer.toBinaryString(b);
		
		for(int i = 0; i < binaryB.length(); i++)
		{
			c = 2 * c;
			d = (d * d) % n;
			if(binaryB.charAt(i) == '1')
			{
				c++;
				d = (d * a) % n;
			}
		}
		
		return d;
	}
	
	/*
	 * Converts a string of length m <= n, to a string of length n by inserting zeroes at the beginning of str.
	 */
	private String toNLengthString(String str, int n)
	{
		if(str.length() == n)
			return str;
		
		String zeroes = new String(new char[n - str.length()]).replace("\0", "0");
		return zeroes + str;
	}
	
		
	/*
	 * Encrypts the message.		//Constraint ----        0 < M < p * q
	 */
	public String encrypt(String message)
	{
		int []cipher = new int[message.length()];
		String cipherString = "";
		for(int i = 0; i < message.length(); i++)
		{
			cipher[i] = encrypt(message.charAt(i));
			cipherString += toNLengthString("" + cipher[i], BLOCK_SIZE);
		}	
	
		System.out.println("STRING = " + cipherString);
		return cipherString;
	}
	/*
	 * Encrypt only an integer
	 */
	public int encrypt(int m)
	{		
		return modularExponentiator(m, public_key, public_key);
	}
	
	/*
	 * Decrypt only  the integer.		//Constraint ----        0 < M < p * q
	 */
	public int decrypt(int c)
	{
		return modularExponentiator(c, private_key, p * q);
	}
	
	public String decrypt(String cipher)
	{
		String plaintext = "";
		int [] message = new int[cipher.length()];
		for(int i = 0; i < cipher.length() / BLOCK_SIZE; i++)
		{
			message[i] = Integer.parseInt(cipher.substring(i * BLOCK_SIZE, i * BLOCK_SIZE + BLOCK_SIZE));
			message[i] = decrypt(message[i]);
			plaintext += (char)message[i];
		}
		return plaintext;
	}	
	
	/*
	 *Display all the details 
	 */
	public String display()
	{
		String message = "Algorithm details \n p = "+p + " q = "+ q + "\nPublic Key is "+public_key+
				"\nPrivate Key is "+private_key+"\n";
		System.out.println(message);
		return message;
	}
	
	public static void main(String args[]) throws InvalidAlgorithmParameterException
	{
		Schmidt_Samoa_Encryptor encryptor = new Schmidt_Samoa_Encryptor();
		encryptor.display();
		
		
		//Constraint ----        0 < M < (p^2) *q
		System.out.println("\n\nMessage (Block) Size Limit = p * q = " + encryptor.p * encryptor.q);
		/*int ciphertext = encryptor.encrypt(210);
		System.out.println("Encrypt = " + ciphertext);
		System.out.println("Decrypt = " + encryptor.decrypt(ciphertext));*/
		
		System.out.println("\nEncrypting String...");
		String message = "Jinkchak";
		String ciphertext = encryptor.encrypt(message);
		System.out.println("Cipher text = " + ciphertext);
		String plaintext = encryptor.decrypt(ciphertext);
		System.out.println("Plaintext = " + plaintext);
	}

}

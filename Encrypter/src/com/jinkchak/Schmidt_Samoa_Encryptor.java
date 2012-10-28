package com.jinkchak;

import java.security.InvalidAlgorithmParameterException;

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
	
	/*
	 * This constructor initializes p,q and other variables.
	 */
	public Schmidt_Samoa_Encryptor()
	{
		p = 13;
		q = 17; // Can use a pool of primes and use a random number generator to select these from the poll.
		public_key = ComputeN();
		try {
			private_key = Modular_Equation_Solver(public_key, 1, Lcm(p-1, q-1));
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/*
	 * Compute and return the value of N = p^2 * q.
	 */
	private int ComputeN()
	{
		return p*p*q;
	}
	/*
	 * Compute the lcm of a and b
	 */
	private int Lcm(int a, int b)
	{
		return (a*b)/Gcd(a,b);
	}
	
	/*
	 * Compute the gcd of a and b.
	 */
	private int Gcd(int a, int b) {
		if (b==0)
			return a;
		return Gcd(b,a%b);
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
			System.out.println(result[0]+" "+result[1]+" "+result[2]);
			return result;
		}
		
		int []result_temp = extendedEuclidsAlgo(b, a%b);
		int []final_result = {result_temp[0],result_temp[2],result_temp[1]-(a/b)*result_temp[2]};
		System.out.println(final_result[0]+" "+final_result[1]+" "+final_result[2]);
		return final_result;
	}
	
	/*
	 * Modular Equation solver.
	 */
	private int Modular_Equation_Solver(int a, int b, int n) throws InvalidAlgorithmParameterException
	{
		int []solution = extendedEuclidsAlgo(a, n);
		int d = solution[0];
		int x1 = solution[1];
		int y1 = solution[2];
		
		if(b%d == 0)
			{
				int value = x1*b/d;
				return CorrectValue(value, n);
			}
		else
			throw new InvalidAlgorithmParameterException();
	}
	
	private int CorrectValue(int value, int n) {
		while(value<0)
		{
			
			value += n;
		}
		System.out.println(value);
		return value%n;
	}

	/*
	 * Encrypts the message.
	 */
	public int[] encrypt(int [] message)
	{
		int []cipher = new int[message.length];
		for(int i=0;i<message.length;++i)
			cipher[i] = encrypt(message[i]);		
		return cipher;
	}
	/*
	 * Encrypt only an integer
	 */
	public int encrypt(int m)
	{
		int N = ComputeN();//public key
		return (int)Math.pow(m, N)%N;
	}
	
	/*
	 * Decrypt only  the integer.
	 */
	public int decrypt(int c)
	{
		int value = (int)Math.pow(c, private_key);
		return CorrectValue(value, p*q);
	}
	
	public int[] decrypt(int[] cipher)
	{
		int [] message = new int[cipher.length];
		for(int i=0; i<cipher.length; ++i)
		{
			message[i] = decrypt(cipher[i]);
		}
		return message;
	}
	
	/*
	 *Display all the details 
	 */
	public void Display()
	{
		System.out.println("Algorithm details");
		System.out.println("p = "+p + " q = "+ q);
		System.out.println("Public Key is "+public_key);
		System.out.println("Private Key is "+private_key);
	}

}

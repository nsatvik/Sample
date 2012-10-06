'''
Created on Sep 23, 2012

@author: nsatvik


Key generation

Choose two large distinct primes p and q and compute N = p^2 * q 
Compute d = multiplicative inverse of N mod lcm(p-1, q-1)
Now N is the public key and d is the private key.


Encryption
To encrypt a message m we compute the ciphertext as c= m^N mod N


Decryption
To decrypt a ciphertext c we compute the plaintext as m = c^d mod N which like for Rabin and RSA can be computed with the Chinese remainder theorem.
'''
def gcd(a,b):
    if b==0:
        return a
    return gcd(b, a%b)
def extended_euclids_algo(a, b):
    if b==0:
        return (a,1,0)
    dxy_1 = extended_euclids_algo(b, a%b);
    dxy = (dxy_1[0],dxy_1[2],dxy_1[1]-((int(a/b)*dxy_1[2])))
    return dxy
def Modular_Eqn_Solver(a,b,n):
    soln = extended_euclids_algo(a, n);
    d = soln[0]
    x1 = soln[1]
    y1 = soln[2]
    
    if d%b == 0:
        return x1*(b/d)%n
    else:
        print 'No solution exits'
        return -1

def encrypt(m, e, n):
    return (m**e) % n

def decrypt(c, d, n):
    return (c**d) % n

def lcm(a,b):
    return (a*b)/gcd(a,b)
p = 13
q = 17

N = p**2 * q

d = Modular_Eqn_Solver(N, 1, lcm(p-1, q-1))

m = 100
c = encrypt(m, N, N)
print 'Cipher Text is ' + str(c)
mes = decrypt(c, d, p*q)
print 'Message is ', mes

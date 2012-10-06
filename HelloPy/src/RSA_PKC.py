'''
Created on Sep 23, 2012

@author: nsatvik

RSA Public Key Cryto-system 

'''
import math


def euclids_gcd(a, b):
    if b==0:
        return a
    return euclids_gcd(b, a%b);

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


def isPrime(n):
    d = int(math.sqrt(n))
    for j in range(2,d+1):
        if n%j == 0:
            return 0
    return 1

def phi(n):
    res = 1.0
    for i in range(2,n+1):
        if isPrime(i) and n%i==0:
            res *= (1-1.0/i)
    return res*n

def find_e(phi_n):
    for i in range(2,n):
        if euclids_gcd(i, phi_n) == 1:
            return i
p = 11
q = 29
#n = p*q
n = 319


phi_n = (p-1)*(q-1)

e = find_e(phi_n) 

d = Modular_Eqn_Solver(e, 1, phi_n)

print 'Public Key is (e,n) = ('+str(e)+','+str(n)+')'
print 'Private Key is (d,n) = ('+str(d)+','+str(n)+')'
m = 100
c = encrypt(m, e, n)
print 'Cipher Text is ' + str(c)
mes = decrypt(c, d, n)
print 'Message is ', mes
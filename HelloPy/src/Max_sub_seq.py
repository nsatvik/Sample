'''
Created on Sep 20, 2012

@author: nsatvik
'''

def max_sub_sequence(nums):
    maxsum = nums[0]
    for i in range(len(nums)):
        sum = 0
        for j in range(i,len(nums)):
            sum += nums[j]
            if sum > maxsum:
                maxsum = sum
    return maxsum
def max_sub_seq_discontinuous(nums):
    n = len(nums)
    maxsum = nums[0]
    for i in range(n):
        for gap in range(2,n):
            sum = 0
            j = i + gap
            while j<n:
                sum += nums[j]
                if (sum + nums[i]) > maxsum :
                    maxsum = sum+nums[i]
                j += 2
        
    return maxsum
case1 = [3,2,7,10]
case2 = [-1,2,4,-3,21, -44]
case3 = [3,2,5,10,7]
case4 = [-1,-2,-3,-4,-5,-9]
case4 = [-1,-2,-3,10,2,8,-4,-5,-9]
#print max_sub_sequence(case1)
print max_sub_seq_discontinuous(case1)
print max_sub_seq_discontinuous(case2)
print max_sub_seq_discontinuous(case3)
print max_sub_seq_discontinuous(case4)


#print max_sub_sequence(case2)
#print max_sub_seq_discontinuous(case2)


            
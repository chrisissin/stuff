/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import java.util.Stack;

/**
 *
 * @author chrisho
 */
public class intstuff {
    
    static class Node{
        int value;
        Node left, right;
    }

    public intstuff() {
    }
    
    public static void main(String[] args) {
        //Stack<Node> astack = new Stack<Node>();
        //astack.add(null)
        //System.out.println("121");
        Stack seqStack = new Stack();
        seqStack.add(1);
        seqStack.add(3);
        seqStack.add(5);
        seqStack.add(23);
        seqStack.add(2);
        int[] SeqNum= {1, 3, 5, 23, 2};
        int Sum = 8;
        boolean answer = findSum(SeqNum, Sum);//checkSum(Sum, seqStack);
        if(answer){
            System.out.println("true");
        }else
        {
            System.out.println("false");
        }
    }
    
    public static boolean findSum (int [] A ,int T){
		int sum = 0 ;
		int j = 0;
		for (int i = 0 ; i < A.length ; ++i) {
			while (j < A.length &&  sum < T) {
				sum += A[j] ;
				j++;
			}			
			if (sum == T) {
				return true ;
			}
			sum -= A[i] ;
		}
						
		return false ;
    }
    
    public static boolean checkSum(int Sum, Stack seqStack)
    {
        boolean hasSum = false;
        if(!seqStack.isEmpty()){
            for(int i = 0; i <= seqStack.size(); i++)
            {
                int nextNum = (int)seqStack.get(1);
                int thisNum = (int)seqStack.pop();
                int tempSum = thisNum  +  nextNum;//  SeqNum[i+1];
                if(Sum == tempSum )
                {
                    hasSum = true;
                    return hasSum;
                }else if(Sum < tempSum)
                {
                    hasSum = false;
                    return hasSum;
                }
                checkSum(Sum, seqStack);
            }
        }
        return hasSum;
    }
    
}

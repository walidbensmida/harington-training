package org.example.solid.s;

public class stest{
    public static void main(String[] args) {
        Adder adder = new Adder();
        ResultDisplayer resultDisplayer = new ResultDisplayer();

        int a = 5;
        int b = 7;

        int result = adder.add(a, b);
        resultDisplayer.displayResult(result);
    }
}

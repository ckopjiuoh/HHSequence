package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String subsequence;
        ArrayList<String> list = new ArrayList();
        while(!Objects.equals(subsequence = reader.readLine(), "")) {
            try {
                if(subsequence.length()>50){
                    throw new Exception();
                }
                BigInteger test = new BigInteger(subsequence);
                list.add(subsequence);

            } catch (Exception e){
                System.out.println("Введенная подпоследовательность не является числом или превышает длину в 50 символов. Попробуйте еще раз");
            }
        }
        for(String s: list) {
            Subsequence sub = new Subsequence(s);
            System.out.println(sub.getIndex().toString());
        }
    }
}

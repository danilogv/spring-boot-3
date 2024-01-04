package com.danilo.springboot3.utility;

import java.util.InputMismatchException;

public class Functions {

    public static Boolean cpfIsValid(String cpf) {
        cpf = cpf.replace(".","");
        cpf = cpf.replace("-","");
        cpf = cpf.trim();

        if (cpf.length() != 11)
            return false;
        if (cpf.equals("00000000000"))
            return false;
        if (cpf.equals("11111111111"))
            return false;
        if (cpf.equals("22222222222"))
            return false;
        if (cpf.equals("33333333333"))
            return false;
        if (cpf.equals("44444444444"))
            return false;
        if (cpf.equals("55555555555"))
            return false;
        if (cpf.equals("66666666666"))
            return false;
        if (cpf.equals("77777777777"))
            return false;
        if (cpf.equals("88888888888"))
            return false;
        if (cpf.equals("99999999999"))
            return false;

        char dig10,dig11;
        int sm,i,r,num,burden;

        try {
            sm = 0;
            burden = 10;
            for (i = 0;i < 9;i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * burden);
                burden = burden - 1;
            }
            r = 11 - (sm % 11);
            if (r == 10 || r == 11)
                dig10 = '0';
            else
                dig10 = (char)(r + 48);
            sm = 0;
            burden = 11;

            for (i = 0;i < 10;i++) {
                num = cpf.charAt(i) - 48;
                sm = sm + (num * burden);
                burden = burden - 1;
            }

            r = 11 - (sm % 11);

            if (r == 10 || r == 11)
                dig11 = '0';
            else
                dig11 = (char)(r + 48);

            if (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10))
                return true;

            return false;
        }
        catch (InputMismatchException ex) {
            return false;
        }

    }

}

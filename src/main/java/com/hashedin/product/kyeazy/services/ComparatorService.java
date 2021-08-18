package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.entities.Employee;

import java.util.Comparator;

public class ComparatorService implements Comparator<Employee>
{
    public int compare(Employee a,Employee b) {
        if (a.getFirstName() == b.getFirstName())
            return 0;
        String str1 = a.getFirstName();
        String str2 = a.getFirstName();
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }
}

package chap1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by raywang on 2017/11/22.
 */
public class Test {
    public static void testBase(UF uf, Scanner sc) {
        long t1 = System.currentTimeMillis();
        int n = sc.nextInt();
        uf.setN(n);
        while (sc.hasNextLine()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.printf("n %d\n", n);
            n--;
            uf.union(a, b);
            sc.nextLine(); // scan to next line
        }
        long t2 = System.currentTimeMillis();
        System.out.printf("components:%d \n%dms passed\n", uf.count(),
                t2 - t1);
    }

    public static void testQuickFind(Scanner sc) {
        System.out.println("Test Quick Find");
        testBase(new QuickFind(), sc);
        System.out.println();
    }

    public static void testQuickUnion(Scanner sc) {
        System.out.println("Test Quick Union");
        QuickUnion uf = new QuickUnion();
        testBase(uf, sc);
        System.out.println();
    }

    public static void testWeightQuickUnion(Scanner sc) {
        System.out.println("Test Weight Quick Union");
        UF uf = new WeightQuickUnion();
        testBase(uf, sc);
        System.out.println();
    }

    public static void testWeightQuickUnionPathCompact(Scanner sc) {
        System.out.println("Test Weight Quick Union Path Copact");
        UF uf = new WeightQuickUnionPathCompact();
        testBase(uf, sc);
        System.out.println();
    }

    public static void main(String[] args) {
        for (String arg : args) {
            Scanner sc = scanner(arg);
//            testWeightQuickUnion(sc);
            testWeightQuickUnionPathCompact(sc);
            sc.close();
        }
    }

    public static Scanner scanner(String arg) {
        try {
            System.out.println(arg);
            FileInputStream f = new FileInputStream(arg);
            Scanner sc = new Scanner(f);
            return sc;
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}

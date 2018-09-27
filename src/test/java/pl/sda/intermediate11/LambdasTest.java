package pl.sda.intermediate11;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;

public class LambdasTest {

    @FunctionalInterface
    public interface SuperChecker {
        boolean check(Integer value);
    }

    private class OddChecker implements SuperChecker {
        @Override
        public boolean check(Integer value) {
            return value % 2 != 0;
        }
    }

    @Test
    void checkerTest() {
        OddChecker oddChecker = new OddChecker();
        Assert.assertTrue(oddChecker.check(3));

        SuperChecker superChecker = new SuperChecker() {
            @Override
            public boolean check(Integer value) {
                return value % 2 != 0;
            }
        };
        Assert.assertTrue(superChecker.check(3));
        SuperChecker lambdaSuperChecker = e -> e % 2 != 0;
    }

    @FunctionalInterface
    public interface MathOperation {
        int operation(int a, int b);

        default String message() {
            return "BUM!";
        }
    }

    @Test
    void mathOperationTest() {
        MathOperation add = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide = (a, b) -> a / b;

        Assertions.assertEquals(20, add.operation(12, 8));
        Assertions.assertEquals(4, subtract.operation(12, 8));
        Assertions.assertEquals(96, multiply.operation(12, 8));
        Assertions.assertEquals(1, divide.operation(12, 8));
    }

    @FunctionalInterface
    public interface MyBiComparator<T, U> {
        int biCompare(T obj1, U obj2);
    }

    @Test
    void compareTwoTypes() {
        Integer number = 20;
        String text = "123";

        MyBiComparator<Integer, String> myBiComparatorTextFirst = (x, y) -> x.toString().compareTo(y);

        Assertions.assertEquals(1, myBiComparatorTextFirst.biCompare(number, text));
    }

    @Test
    void checkDefaultValues() {
        int[] array = new int[10];
        for (int i : array) {
            System.out.println(i);
        }
    }

    @Test
    void sortArray() {
        int[] firstArray = {222, 48, 19, 3, 192322};
        int[] ints = Arrays.stream(firstArray).sorted().toArray();
        org.apache.commons.lang3.ArrayUtils.reverse(ints);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    @Test
    void sorttArray() {
        int[] array = {50, 160, 40, 20, 230, 300};
        boolean flag = true;
        int temp;
        while (flag) {
            flag = false;

            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] < array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    flag = true;
                }
            }
        }
        for (int i : array) {
            System.out.println(i);
        }
    }

    @Test
    void check(){
        int a = 10;
        int b = a;
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        a++;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        int[] arr = {2, 3, 5, 8};
        int[] array = arr;

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(array));

        arr = new int[]{2, 3, 5, 9, 23, 11, 34, 55};

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(array));


    }
}


package pl.sda.intermediate11.bookstore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringCalculatorTest {

    @Test
    void shouldReturnZeroWhenTextIsEmpty() {
        String exampleData = "";

        int result = StringCalculator.adding(exampleData);

        assertEquals(0,result);
    }

    @Test
    void shouldReturnZeroWhenTextIsBlank() {
        String exampleData = " ";

        int result = StringCalculator.adding(exampleData);

        assertEquals(0,result);
    }

    @Test
    void shouldReturnNumberWhenDataIsOneNumber() {
        String exampleData1 = "7";
        String exampleData2 = "7 ";

        int result1 = StringCalculator.adding(exampleData1);
        int result2 = StringCalculator.adding(exampleData2);

        assertEquals(7,result1);
        assertEquals(7,result2);
    }

    @Test
    void shouldReturnSumWhenTextHasTwoNumbersWithDelimiter() {
        String exampleData = " 1   , 2 ";

        int result = StringCalculator.adding(exampleData);

        assertEquals(3,result);
    }

    @Test
    void shouldReturnSumWhenTextHasMoreNumbersWithDelimiter() {
        String exampleData = " 1   , 2 , 3  , 5  , 88,  ,,, ";

        int result = StringCalculator.adding(exampleData);

        assertEquals(99,result);
    }

    @Test
    void shouldReturnSumWhenTextHasMoreNumbersWithDelimiterOrNewLine() {
        String exampleData = " 1   , 2 , 3  \n5   \n88,  ,,, ";

        int result = StringCalculator.adding(exampleData);

        assertEquals(99,result);
    }

    @Test
    void shouldReturnSumOfManyNumbersSeperatedWithCustomDelimiter() {
        String exampleData = "   //;\n1 ; 1   ; 2 ; 3  ;5   ;88;  ;;; ";

        int result = StringCalculator.adding(exampleData);

        assertEquals(100,result);
    }
}
package pl.sda.intermediate11;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class GenericsTest {
    //Napisz generyczną metodę która przyjmuje Listę jakichkolwiek elementów i wypisuje
    // wszystkie elementy tej listy, ale z zachowaniem kolejności przekazanej w parametrze


    @Test
    void test1() {
        printAndSort(Lists.newArrayList("a","c","b"), (a,b)->a.compareTo(b));
        printAndSort(Lists.newArrayList(3,8,11,"0"),(a,b)->a.toString().compareTo(b.toString()));
        sumNubers(Lists.newArrayList(22,18,34),(a -> a > 19));

        Pair<Integer, String> pair1 = new Pair<>();
        Pair<Integer, String> pair2 = new Pair<>();

        pair1.setObj1(1);
        pair2.setObj1(1);
        pair1.setObj2("2");
        pair2.setObj2("2");
        System.out.println(pair1.equals(pair2));
    }

    private <E> void printEverything(List<E> list) {
        for (E element : list) {
            System.out.printf(element.toString());
        }
    }

    private <E extends Comparable> void printAndSort(List<E> list, Comparator<E> comparator) {
        list.stream().sorted(comparator).forEach(e-> System.out.println(e));
    }

    private <E extends Number> void sumNubers(List<E> list, Predicate<Integer> integerPredicate) {
        list.stream()
                .map(e -> e.intValue())
                .filter(integerPredicate)
                .reduce((a,b)->a+b)
                .orElse(0);
    }

    private boolean compareTwoPairs(Pair pair1, Pair pair2) {
        return pair1.equals(pair2);
    }

}

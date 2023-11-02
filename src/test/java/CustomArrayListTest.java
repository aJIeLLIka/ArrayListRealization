import customarray.CustomArrayList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {

    @Test
    void testAddAndGet() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
        assertEquals(3, list.getSize());
    }

    @Test
    void testAddByIndex() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add(0, "first");
        list.add(1, "second");
        list.add(0, "third");
        assertEquals("third", list.get(0));
        assertEquals("first", list.get(1));
        assertEquals("second", list.get(2));
        assertEquals(3, list.getSize());
    }

    @Test
    void testReplace() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.replace(1, "replaced");
        assertEquals(3, list.getSize());
        assertEquals("replaced", list.get(1));
    }

    @Test
    void testRemove() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.remove(2);
        list.remove(0);
        assertEquals(1, list.getSize());
        assertEquals("second", list.get(0));
    }

    @Test
    void testRemoveWithNotExistIndex() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(3);
        });
    }

    @Test
    void testClear() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.clear();
        assertEquals(0, list.getSize());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });
    }

    @Test
    void testSort() {
        Random random = new Random();
        CustomArrayList<Integer> listInteger = new CustomArrayList<>();
        int[] arrayInteger = new int[100];
        for (int i = 0; i < arrayInteger.length; i++) {
            int randomNumber = random.nextInt(100);
            arrayInteger[i] = randomNumber;
            listInteger.add(randomNumber);
        }

        Arrays.sort(arrayInteger);
        listInteger.sort(Integer::compareTo);

        for (int i = 0; i < listInteger.getSize(); i++) {
            assertEquals(arrayInteger[i], listInteger.get(i));
        }
    }
}

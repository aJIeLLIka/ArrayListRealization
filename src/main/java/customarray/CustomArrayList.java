package customarray;

import java.util.Comparator;

/**
 * Реализация собственного ArrayList
 *
 * @param <T> обобщенный тип
 * @author Alexei Antonchyk
 */
public class CustomArrayList<T> {

    private static final int DEFAULT_INIT_CAPACITY = 10;
    private Object[] elements;

    private int size;

    /**
     * Создает пустой список список с начальной емкостью DEFAULT_INIT_CAPACITY
     */
    public CustomArrayList() {
        this.elements = new Object[DEFAULT_INIT_CAPACITY];
    }

    /**
     * Создает пустой список с указанной начальной емкостью
     *
     * @param initCapacity начальная емкость списка
     */
    public CustomArrayList(int initCapacity) {
        this.elements = new Object[initCapacity];
    }

    /**
     * Возвращает количество элементов в списке
     *
     * @return количество элементов в списке
     */
    public int getSize() {
        return size;
    }

    /**
     * Добавление элемента в конец списка
     *
     * @param element добавляемый элемент
     */
    public void add(T element) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = element;
    }

    /**
     * Добавление элемента в список по указанному индексу
     *
     * @param index   индекс для добавления
     * @param element элемент для добавления
     * @throws IndexOutOfBoundsException если индекс вне допустимого диапазона
     */
    public void add(int index, T element) {
        indexRangeCheckForAdd(index);
        if (size == elements.length) {
            grow();
        }
        shiftElementsRight(index, size);
        elements[index] = element;
        size++;
    }

    /**
     * Получение элемента по индексу
     *
     * @param index индекс элемента
     * @return элемент по указанному индексу
     * @throws IndexOutOfBoundsException если элемента с таким индексом не существует
     */
    public T get(int index) {
        indexRangeCheck(index);
        return (T) elements[index];
    }

    /**
     * Замена элемента по индексу
     *
     * @param index   индекс заменяемого элемента
     * @param element новый элемент
     * @throws IndexOutOfBoundsException если элемента с таким индексом не существует
     */
    public void replace(int index, T element) {
        indexRangeCheck(index);
        elements[index] = element;
    }

    /**
     * Удаление элемента по индексу
     *
     * @param index индекс удаляемого элемента
     * @throws IndexOutOfBoundsException если элемента с таким индексом не существует
     */
    public void remove(int index) {
        indexRangeCheck(index);
        shiftElementsLeft(index + 1, size);
        elements[--size] = null;
    }

    /**
     * Удаление всех элементов списка
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Сортирует список с использованием алгоритма QuickSort
     *
     * @param comparator компаратор для сравнения элементов
     */
    public void sort(Comparator<? super T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    /**
     * Рекурсивная сортировка элементов подмассива в указанном диапазоне
     *
     * @param from       начало подмассива
     * @param to         конец подмассива
     * @param comparator компаратор для сравнения элементов
     */
    private void quickSort(int from, int to, Comparator<? super T> comparator) {
        if (from < to) {
            int pivotIndex = partition(from, to, comparator);
            quickSort(from, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, to, comparator);
        }
    }

    /**
     * Разделение массива на две части вокруг опорного элемента.
     *
     * @param from       начало подмассива
     * @param to         конец подмассива
     * @param comparator компаратор для сравнения элементов
     * @return индекс опорного элемента
     */
    private int partition(int from, int to, Comparator<? super T> comparator) {
        T pivotElement = (T) elements[to];
        int i = from - 1;
        for (int j = from; j < to; j++) {
            if (comparator.compare((T) elements[j], pivotElement) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, to);
        return i + 1;
    }

    /**
     * Замена двух элементов местами по указаным индексам
     *
     * @param index1 индекс первого элемента
     * @param index2 индекс второго элемента
     */
    private void swap(int index1, int index2) {
        Object temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    /**
     * Проверка существует ли элемент по указанному индексу
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если элемента с таким индексом не существует
     */
    private void indexRangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }
    }

    /**
     * Проверка можно ли добавить элемент по указанному индексу
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если индекс вне допустимого диапазона
     */
    private void indexRangeCheckForAdd(int index) {
        if (index >= size + 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }
    }

    /**
     * Увеличение емкости списка.
     * Создание нового списка с бОльшей емкостью с копированием всех элементов
     */
    private void grow() {
        int newCapacity = (int) (elements.length * 1.5);
        Object[] newElementArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newElementArray, 0, size);
        elements = newElementArray;
    }

    /**
     * Сдвиг элементов вправо на одну позицию в указанном подмассиве
     *
     * @param from индекс начала подмассива
     * @param to   индекс конца подмассива
     */
    private void shiftElementsRight(int from, int to) {
        int elementsToBeShifted = to - from;
        System.arraycopy(elements, from, elements,
                from + 1, elementsToBeShifted);
    }

    /**
     * Сдвиг элементов влево на одну позицию в указанном подмассиве
     *
     * @param from индекс начала подмассива
     * @param to   индекс конца подмассива
     */
    private void shiftElementsLeft(int from, int to) {
        int elementsToBeShifted = to - from;
        System.arraycopy(elements, from, elements,
                from - 1, elementsToBeShifted);
    }
}

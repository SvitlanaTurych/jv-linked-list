package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (tail == null) {
            head = newNode;;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        checkIndexForAdd(index);
        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> newNode;
        checkIndex(index);
        newNode = head;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode.element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode;
        checkIndex(index);
        newNode = head;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        T oldValue;
        oldValue = newNode.element;
        newNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> newNode = head;
        checkIndex(index);
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        if (index == 0) {
            if (head == tail) {
                head = null;
                tail = null;
                size--;
                return newNode.element;
            }
            head = newNode.next;
            head.prev = null;
            size--;
            return newNode.element;
        }
        if (index == size - 1) {
            tail = newNode.prev;
            tail.next = null;
            size--;
            return newNode.element;
        }
        newNode.prev.next = newNode.next;
        newNode.next.prev = newNode.prev;
        size--;
        return newNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null) {
                if (newNode.element == null) {
                    unlink(newNode);
                    return true;
                }
            }
            if (object != null) {
                if (object.equals(newNode.element)) {
                    unlink(newNode);
                    return true;
                }
            }
            newNode = newNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public void checkIndexForAdd(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public boolean unlink(Node<T> newNode) {
        if (head == tail) {
            tail = null;
            head = null;
            size--;
            return true;
        }
        if (newNode == head) {
            head = newNode.next;
            head.prev = null;
            size--;
            return true;
        } else if (newNode == tail) {
            tail = newNode.prev;
            tail.next = null;
            size--;
            return true;
        }
        newNode.prev.next = newNode.next;
        newNode.next.prev = newNode.prev;
        size--;
        return true;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        Node(T element) {
            this.element = element;
        }
    }
}

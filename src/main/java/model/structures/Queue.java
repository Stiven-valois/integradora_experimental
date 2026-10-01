package model.structures;

import java.util.LinkedList;
import model.exceptions.EmptyStructureException;

public class Queue<T> {

    private final LinkedList<T> list;


    public Queue() {
        this.list = new LinkedList<>();
    }

    public void enqueue(T item) {
        list.addLast(item);
    }


    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyStructureException("No se puede realizar dequeue en una cola vacía.");
        }
        return list.removeFirst();
    }


    public T front() {
        if (isEmpty()) {
            throw new EmptyStructureException("No se puede consultar front en una cola vacía.");
        }
        return list.getFirst();
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }


    public int size() {
        return list.size();
    }
}
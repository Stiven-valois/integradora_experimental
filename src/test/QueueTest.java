package model.structures;

import model.exceptions.EmptyStructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    private Queue<String> queue;

    @BeforeEach
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void testEnqueueAndDequeueFIFO() {
        queue.enqueue("Incidente_1");
        queue.enqueue("Incidente_2");
        queue.enqueue("Incidente_3");

        assertEquals(3, queue.size());
        assertEquals("Incidente_1", queue.front());

        assertEquals("Incidente_1", queue.dequeue());
        assertEquals("Incidente_2", queue.dequeue());
        assertEquals("Incidente_3", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDequeueOnEmptyQueueThrowsException() {
        assertThrows(EmptyStructureException.class, () -> {
            queue.dequeue();
        });
    }

    @Test
    public void testFrontOnEmptyQueueThrowsException() {
        assertThrows(EmptyStructureException.class, () -> {
            queue.front();
        });
    }
}
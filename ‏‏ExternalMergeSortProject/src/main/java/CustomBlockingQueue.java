import java.util.concurrent.ArrayBlockingQueue;

public class CustomBlockingQueue<E> extends ArrayBlockingQueue<E> {
    private volatile boolean shouldTerminate = false;

    public CustomBlockingQueue(int capacity) {
        super(capacity);
    }

    // Add a method to signal the queue to terminate
    public void terminate() {
        shouldTerminate = true;
        // You can also interrupt any waiting threads if necessary
    }

    // Override or extend the existing queue methods to check for termination
    @Override
    public E poll() {
        if (shouldTerminate) {
            // Perform termination logic
            return null; // or some other sentinel value
        }
        return super.poll();
    }

    public boolean isShouldTerminate() {
        return shouldTerminate;
    }
}

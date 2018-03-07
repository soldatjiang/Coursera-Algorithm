import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;
    private int head;
    private int tail;

    public RandomizedQueue(){
        s=(Item[])new Object[1];
        N=head=tail=0;
    }

    public boolean isEmpty(){
        return N==0;
    }

    public int size(){
        return N;
    }

    public void enqueue(Item item){
        if(item==null)
            throw new IllegalArgumentException("Enqueue a null argument...");
        else {
            if(tail==s.length-1)
                resize(2*s.length);

            s[tail++]=item;
            N++;
        }
    }

    public Item dequeue(){
        if(isEmpty())
            throw new NoSuchElementException("Empty queue");

        int index=StdRandom.uniform(tail);
        Item item;

        while(s[index]==null)
            index=StdRandom.uniform(tail);

        item=s[index];
        s[index]=null;
        N--;

        if(index==head){
            while(s[head]==null && head!=tail)
                head++;
        }

        if(N>0 && N==s.length/4)
            resize(s.length/2);
        return item;
    }

    public Item sample(){
        int index=StdRandom.uniform(tail);

        if(isEmpty())
            throw new NoSuchElementException("Empty queue");
        while(s[index]==null)
            index=StdRandom.uniform(tail);

        return s[index];
    }

    private void resize(int capacity){
        Item[] copy=(Item[])new Object[capacity];
        int j=0;
        for(int i=head;i<=tail;i++){
            if(s[i]!=null)
                copy[j++]=s[i];
        }

        tail=j;
        s=copy;
    }

    public Iterator<Item> iterator() {
        return new RanQueueIterator();
    }

    private class RanQueueIterator implements Iterator<Item>{
        private int i=head;

        public boolean hasNext() {
            return i!=tail;
        }

        public Item next() {
            int index=i;
            if(!hasNext())
                throw new NoSuchElementException("No more item to return");

            if(++i!=tail){
                for(;i<tail;i++){
                    if(s[i]!=null)
                        break;
                }
            }

            return s[index];
        }

        public void remove() {
            /*Unsupported function*/
            throw new UnsupportedOperationException("remove operation unsupported.");
        }
    }

    public static void main(String[] args){
        RandomizedQueue<Integer> rq=new RandomizedQueue();
        //rq.dequeue();
        rq.enqueue(8);
        rq.dequeue();
        rq.enqueue(7);
        rq.enqueue(9);
        rq.enqueue(10);
        StdOut.println(rq.dequeue());
        StdOut.print("\n");
        //StdOut.println(rq.dequeue());
        for(int i:rq)
            StdOut.println(i);
    }
}

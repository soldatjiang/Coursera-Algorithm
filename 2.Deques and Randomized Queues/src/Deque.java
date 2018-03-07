import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item>{
    private Node first=null;
    private Node last=null;
    private int N=0;

    private class Node{
        Item item;
        Node prev;
        Node next;

        private Node() {
            item = null;
            prev=next=null;
        }
    }

    public Deque(){ //construct an empty deque

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void addFirst(Item item){
        if(item==null)
            throw new IllegalArgumentException("addFirst: NUll argument");
        else if(N==0) {
            first = last = new Node();
            first.item = last.item = item;
            N++;
        }else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev=first;
            N++;
        }
    }

    public void addLast(Item item){
        if(item==null)
            throw new IllegalArgumentException("addLast: Null argument");
        else if(N==0) {
            first = last = new Node();
            first.item = last.item = item;
            N++;
        }else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            oldlast.next = last;
            last.prev=oldlast;
            N++;
        }
    }

    public Item removeFirst(){
        Item item;
        if(isEmpty())
            throw new NoSuchElementException("Deque is empty");
        else if(N==1){
            item=first.item;
            first=last=null;
            N--;
            return item;
        }else {
            item = first.item;
            first = first.next;
            first.prev=null;
            N--;
            return item;
        }
    }

    public Item removeLast(){
        Item item;
        if(isEmpty())
            throw new NoSuchElementException("Deque is empty");
        else if(N==1){
            item=last.item;
            first=last=null;
            N--;
            return item;
        }else {
            item=last.item;
            last=last.prev;
            last.next=null;
            N--;
            return item;
        }
    }

    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current=first;


        public boolean hasNext() {
            return current!=null;
        }

        public Item next() {
            if(current==null)
                throw new NoSuchElementException("No next to return");

            Item item=current.item;
            current=current.next;
            return item;
        }

        public void remove(){
            throw new UnsupportedOperationException("Remove not implemented");
        }
    }

    public static void main(String[] args){ //unit test
        /*Deque<Integer> dq=new Deque();
        dq.addFirst(8);
        dq.addFirst(7);
        dq.addLast(9);
        dq.addFirst(0);
        dq.removeLast();
        dq.addLast(12);
        //.iterator().remove();
        for (int n: dq) {
            StdOut.println(n);
           // StdOut.println(' ');
        }
        StdOut.println(dq.size());*/
    }
}

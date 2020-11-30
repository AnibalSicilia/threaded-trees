package OptionalAssignment;

import java.util.ArrayList;
import java.util.Stack;

/* COURSE      : COP 3530
 * Section     : U02
 * Semester    : Fall 2012
 * Instructor  : Alex Pelin
 * Author      : Anibal Sicilia
 * Assignment #: optional
 * Due date    : November 29 , 2012
 * Description : An implementation of the Threaded Node class 
 *
 *
 *
 *  I certify that the work is my own and did not consult with
 *  anyone.
 *
 * Anibal Sicilia 
 * 
 */
/**
 * This class describes the nodes of a tree threaded in inorder
 * @author Myself
 * @param <T> 
 */
public class ThreadedNode<T>
{
    // the fields
    private T element; // the element
    // lThread is true if the left child is a thread and false if it is not
    private boolean lThread = false;
    private ThreadedNode<T> left; // the left child
    // rThread is true if the right child is a thread and false if it is not
    private boolean rThread = false;
    private ThreadedNode<T> right; // the right child
    
    // create a node with a given value and children
    // the threads are set to false
    public ThreadedNode(T theElement, ThreadedNode<T> lt, ThreadedNode<T> rt)
    {
        element = theElement;
        left = lt;
        right = rt;
    }
    
    // thread the binary tree when you are given the root 
    public ThreadedNode(BinaryNode<T> root)
    {
        ThreadedNode<T> t = theThread(null, null, root);
        element = t.element;
        left = t.left;
        right = t.right;
        
    }
    
    private ThreadedNode<T> theThread(ThreadedNode<T> lT, ThreadedNode<T> rT, BinaryNode<T> root)
    {
        if (root == null)
            return null;
        ThreadedNode<T> threaded = new ThreadedNode(root.getElement(), null, null);
        threaded.left =  theThread(lT, threaded, root.getLeft());
        threaded.right = theThread(threaded, rT, root.getRight());

        if (threaded.left == null && lT != null) 
        {
            threaded.left = lT;
            threaded.setLeftThread(true);
        }
        if (threaded.right == null && rT != null)  
        {
            threaded.right = rT;
            threaded.setRightThread(true);
        }
        return threaded;
    }
    
    /**
     * Print the tree elements in inorder traversal
     */
    public void printInOrder()
    {
        // write an iterative method that prints
        // the items of a threaded tree in inorder
        ThreadedNode<T> current = this;
        current = insucc(current);
        
        while(current != null)
        {
            System.out.print(current.element + ", ");
            if(current.rThread)
            {
                current = current.right;
            }
            else
            {
                current = insucc(current.right);
            }
        }
    }
    
    private ThreadedNode<T> insucc(ThreadedNode<T> node)
    {
        ThreadedNode<T> current = node;
        
        if(current == null)
            return current;
        while(current.getLeft() != null && !current.isLeftAThread())
        {
            current = current.getLeft();
        }
        return current;
    }
    
    // return the element
    public T getElement()
    {
        return element;
    }
    
    // return lThread
    public boolean isLeftAThread()
    {
        return lThread;
    }
    
    // return the left child
    public ThreadedNode<T> getLeft()
    {
        return left;
    }
    
    // return rThread
    public boolean isRightAThread()
    {
        return rThread;
    }
    
    // return the right child
    public ThreadedNode<T> getRight()
    {
        return right;
    }
    
    /**
     * Set the element to a given value
     * @param x 
     */
    public void setElement(T x)
    {
        element = x;
    }
    
    /**
     * Set the left child
     * @param t is the left child
     */
    public void setLeft(ThreadedNode<T> t)
    {
        left = t;
    }
    
    /**
     * Set the right child
     * @param t is the right child
     */
    public void setRight(ThreadedNode<T> t)
    {
        right = t;
    }
    
    /**
     * set the left thread
     * @param newT is the new thread
     */
    public void setLeftThread(boolean newT)
    {
        lThread = newT;
    }
    
    /**
     * Set the right thread
     * @param newT is the new thread
     */
    public void setRightThread(boolean newT)
    {
        rThread = newT;
    }
    

    
    
    /**
     * Threads the tree. However, this time the threading produces a 
     * binary tree that does not keep track of weather the child is
     * genuine or added
     */
    public static <T> BinaryNode<T> thread(BinaryNode<T> root)
    {
        return bThread(null, null, root);
    }
    
    private static <T> BinaryNode<T> bThread(BinaryNode<T> lRef, BinaryNode<T> rRef, BinaryNode<T> bRoot)
    {
        if(bRoot == null)
            return null;
        
        BinaryNode<T> bTh = new BinaryNode<>(bRoot.getElement(), null, null);
        BinaryNode<T> l = bThread(lRef, bTh, bRoot.getLeft());
        BinaryNode<T> r = bThread(bTh, rRef, bRoot.getRight());
        
        if(l == null && lRef != null)
            l = lRef;
        bTh.setLeft(l);
        
        if(r == null && rRef != null)
            r = rRef;
        bTh.setRight(r);
        return bTh;            
    }
    
    /**
     * Takes as input the output of the preceding method and returns
     * the original tree
     */
    public static <T> BinaryNode <T> unthread(BinaryNode<T> root)
    {
        Stack <BinaryNode <T>> s = new Stack();
        ArrayList <BinaryNode <T>> array = new ArrayList<>();
        BinaryNode <T> current = root;
        BinaryNode tmp = null;
        
        while(current != null || !s.isEmpty())
        {
            if(current != null)
            {
                    s.push(current);
                    array.add(current);
                    tmp = current;

                current = current.getLeft();

                if(current != null && current.getLeft() != null)
                {
                    if(array.contains(current.getLeft()))
                        if(array.indexOf(current.getLeft()) < array.indexOf(tmp))
                        {
                            current.setLeft(null);
                            current = tmp;
                            s.pop();
                            array.remove(tmp);
                            continue;
                        }
                }
            }
            else
            {
                current = s.pop();
                tmp = current;
                if(current != null && current.getRight() != null)
                {
                    if(array.contains(current.getRight()))
                        if(array.indexOf(current.getRight()) < array.indexOf(tmp))
                            current.setRight(null);
                }
                current = current.getRight();
                
                if(current != null && current.getLeft() != null)
                {
                    if(array.contains(current.getLeft()))
                        current.setLeft(null);
                }
            }
        }
        return root;
    } 
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

/**
 *
 * @author Cornel
 */
public interface Stack
{
	public void push(Object o);
	public Object pop();
	public Object peek();
	public int size();
	public boolean isEmpty();
}

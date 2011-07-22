package com.tonbeller.wcf.controller;

import java.util.Stack;

/**
 * Maintains a stack in a ThreadLocal. If the stack is empty, the ThreadLocal is cleared,
 * so there is no memory leak.
 * <p/>
 * Implementation note: its not necessary to synchronize because every Thread
 * uses its own instance of the java.util.Stack. 
 * 
 * @author av
 * @since Sep 12, 2005
 */
class ThreadLocalStack {
  ThreadLocal tl = new ThreadLocal();

  public void push(Object obj) {
    Stack stack = (Stack) tl.get();
    if (stack == null) {
      stack = new Stack();
      tl.set(stack);
    }
    stack.push(obj);
  }

  /**
   * @param failIfEmpty if true, an EmptyThreadLocalStackException is thrown
   * when called on an empty stack. If false, null is returned for an empty stack.
   */
  public Object peek(boolean failIfEmpty) throws EmptyThreadLocalStackException {
    Stack stack = (Stack) tl.get();
    if (stack == null || stack.isEmpty()) {
      if (failIfEmpty)
        throw new EmptyThreadLocalStackException();
      return null;
    }
    return stack.peek();
  }


  public void pop() {
    Stack stack = (Stack) tl.get();
    if (stack == null || stack.isEmpty())
      throw new EmptyThreadLocalStackException();
    stack.pop();
    if (stack.isEmpty())
      tl.set(null);
  }

}

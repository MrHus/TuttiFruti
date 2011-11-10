package stack;

/**
 *
 * @author Cornel Alders
 */
public class TestListStack
{
	private Stack stack;

    public TestListStack()
    {
        stack = new ListStack();

		System.out.println("isEmpty() -> Expected: true");
		System.out.println(stack.isEmpty()); //true

		stack.push("Een");
		stack.push("Twee");
		stack.push("Drie");
		
		System.out.println("isEmpty() -> Expected: false");
		System.out.println(stack.isEmpty()); //false

		System.out.println("peek() -> Expected: Drie");
		System.out.println(stack.peek());

		System.out.println("pop() -> Expected: Drie");
		System.out.println(stack.pop());

		System.out.println("pop() -> Expected: Twee");
		System.out.println(stack.pop());

		System.out.println("size() -> Expected: 1");
		System.out.println(stack.size());

		System.out.println("pop() -> Expected: Een");
		System.out.println(stack.pop());

		System.out.println("pop() -> Expected: null");
		System.out.println(stack.pop());

		System.out.println("size() -> Expected: 0");
		System.out.println(stack.size());
    }

	public static void main(String[] args)
	{
		new TestListStack();
	}
}

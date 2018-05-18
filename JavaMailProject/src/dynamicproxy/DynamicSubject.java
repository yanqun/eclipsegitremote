package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/*
 * 该代理类的内部属性是Object类型，实际使用的时候通过该类的构造方法传递进来一个对象，
 * 此外，该类还实现了invoke方法，该方法中的method.invode其实就是调用被代理对象的将要执行的方法，方法的参数是sub,
 * 表示该方法从属于sub,通过动态代理类，我们可以在执行真实对象的方法前后加入一些自己的额外方法。
 */
public class DynamicSubject  implements InvocationHandler{
	//代理的真实对象(可以代理任何真实类型，因此不是RealSubject)
	private Object sub ;
	
	public DynamicSubject(Object sub) {
		this.sub = sub;
	}

	//返回值：代理方法的返回值
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before..."+method);
		
		method.invoke(sub, args) ;
		System.out.println("after..."+method);
		
		return null;
	}

}

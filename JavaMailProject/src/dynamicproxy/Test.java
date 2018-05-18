package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		RealSubject realSubject = new RealSubject() ;
		InvocationHandler proxyHandler = new DynamicSubject(realSubject) ;
		
		
		Class classType = proxyHandler.getClass();
		//一次性生成代理
		Subject subject = (Subject) Proxy.newProxyInstance(classType.getClassLoader(), realSubject.getClass().getInterfaces(),proxyHandler);			
		subject.request();
		System.out.println(subject.getClass());
	}
}

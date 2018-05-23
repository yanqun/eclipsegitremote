package dynamicproxy;

//123
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/*
 * �ô�������ڲ�������Object���ͣ�ʵ��ʹ�õ�ʱ��ͨ������Ĺ��췽�����ݽ���һ������
 * ���⣬���໹ʵ����invoke�������÷����е�method.invode��ʵ���ǵ��ñ��������Ľ�Ҫִ�еķ����������Ĳ�����sub,
 * ��ʾ�÷���������sub,ͨ����̬�����࣬���ǿ�����ִ����ʵ����ķ���ǰ�����һЩ�Լ��Ķ��ⷽ����
 */
public class DynamicSubject  implements InvocationHandler{
	//�������ʵ����(���Դ����κ���ʵ���ͣ���˲���RealSubject)
	private Object sub ;
	
	public DynamicSubject(Object sub) {
		this.sub = sub;
	}

	//����ֵ���������ķ���ֵ
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before..."+method);
		
		method.invoke(sub, args) ;
		System.out.println("after啊阿斯达..."+method);
		
		return null;
	}

}

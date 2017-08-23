package br.com.solimar.finan.core.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


/*NÃO FUNCIONOU
* A classe que será interceptada deverá ser anotada com @Interceptors({ExampleInterceptor.class})
*/

public class ExampleInterceptor {

	@AroundInvoke
	public Object intercepta(InvocationContext context) throws Exception {

		long millis = System.currentTimeMillis();

		// chamada do método do interceptado
		Object o = context.proceed();

		String metodo = context.getMethod().getName();
		String nomeClasse = context.getTarget().getClass().getSimpleName();
		System.out.println(nomeClasse + ", " + metodo);
		System.out.println("Tempo gasto: " + (System.currentTimeMillis() - millis));

		return o;
	}

}

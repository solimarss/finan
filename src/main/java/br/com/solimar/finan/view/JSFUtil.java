package br.com.solimar.finan.view;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtil {

	public static String getUrlParameter(String parameterName) {
		FacesContext fContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fContext.getExternalContext().getRequest();
		String parameter = request.getParameter(parameterName);
		return parameter;
	}

}

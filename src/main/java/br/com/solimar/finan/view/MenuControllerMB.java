package br.com.solimar.finan.view;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class MenuControllerMB {
	
	
	public String getItemCssClass(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();
		
		//viewId = "/pages/" + viewId + ".xhtml";
		viewId = "/pages/" + viewId;
		
		//log.info("[getItemCssClass] currentViewId: "+currentViewId);
		
		return currentViewId.contains(viewId) ? "is-selected" : null;
	}

}

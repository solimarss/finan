package br.com.solimar.finan.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuControllerMB implements Serializable{
	
	
	public String getItemCssClass(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();
		
		//viewId = "/pages/" + viewId + ".xhtml";
		viewId = "/pages/" + viewId;
		
		//log.info("[getItemCssClass] currentViewId: "+currentViewId);
		System.out.println("viewId "+viewId);
		System.out.println("currentViewId "+currentViewId);
		
		return currentViewId.contains(viewId) ? "is-selected" : null;
	}

}

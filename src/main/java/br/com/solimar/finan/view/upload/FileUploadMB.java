package br.com.solimar.finan.view.upload;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.com.solimar.finan.business.FileImportRN;

@Named
@ViewScoped
public class FileUploadMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FileImportRN fileImportRN;

	public void importarExtratoBancario(FileUploadEvent event) {
		
		
		
		try {
			fileImportRN.importarExtratoBancario(event.getFile().getInputstream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
package br.com.solimar.finan.view.upload;

import java.io.IOException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.com.solimar.finan.business.FileImportRN;

@Named
@ViewScoped
public class UploadMB {

	@Inject
	private FileImportRN fileImportRN;

	public void importarExtratoBancario(FileUploadEvent event) {
		
		try {
			fileImportRN.importarExtratoBancario(event.getFile().getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
package br.com.solimar.finan.view.upload;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.com.solimar.finan.business.FileImportRN;
import br.com.solimar.finan.view.application.UIService;
import net.sf.ofx4j.io.OFXParseException;

@Named
@ViewScoped
public class FileUploadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FileImportRN fileImportRN;

	public void importarExtratoBancario(FileUploadEvent event) {

		try {
			fileImportRN.importarExtratoBancario(event.getFile().getInputstream());
		} catch (IOException e) {
			UIService.showError("IOException", e);
		} catch (OFXParseException e) {
			UIService.showError("OFXParseException", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			UIService.showError(e);
		}

	}

	public void importarCartaoCredito(FileUploadEvent event) {

		
			
			try {
				fileImportRN.importarCartaoCredito(event.getFile().getInputstream());
				
			} catch (IOException e) {
				UIService.showError("IOException", e);
			} catch (OFXParseException e) {
				UIService.showError("OFXParseException", e);
			}catch (Exception e) {
				UIService.showError("Exception", e);
			}
			
		

	}

}
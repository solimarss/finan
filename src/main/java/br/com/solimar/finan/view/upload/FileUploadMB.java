package br.com.solimar.finan.view.upload;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

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

	private static final long serialVersionUID = 1L;
	@Inject
	private FileImportRN fileImportRN;
	private Date dataPagamentoFatura;

	public void openDialogExtrato() {
		UIService.show("extrato_upload_wvar");
	}

	public void openDialogFatura() {

		UIService.update("fatura_upload_form_id");
		UIService.show("fatura_upload_wvar");
	}

	public void importarExtratoBancario(FileUploadEvent event) {

		try {
			fileImportRN.importarExtratoBancario(event.getFile().getInputstream());
			UIService.showSuccess();
			UIService.hide("extrato_upload_wvar");
		} catch (IOException e) {
			UIService.showError("IOException", e);
		} catch (OFXParseException e) {
			UIService.showError("OFXParseException", e);
		} catch (Exception e) {
			e.printStackTrace();
			UIService.showError(e);
		}

	}


	
	public void importarCartaoCredito(FileUploadEvent event) {
		System.out.println("Data da Fatura: "+dataPagamentoFatura);

		try {
			fileImportRN.importarCartaoCredito(event.getFile().getInputstream(), dataPagamentoFatura);
			UIService.showSuccess();
			UIService.hide("fatura_upload_wvar");
		} catch (IOException e) {
			UIService.showError("IOException", e);
		} catch (OFXParseException e) {
			UIService.showError("OFXParseException", e);
		} catch (Exception e) {
			UIService.showError("Exception", e);
		}

	}

	public Date getDataPagamentoFatura() {
		System.out.println("getDataPagamentoFatura: "+dataPagamentoFatura);
		return dataPagamentoFatura;
	}

	public void setDataPagamentoFatura(Date dataPagamentoFatura) {
		System.out.println("setDataPagamentoFatura: "+dataPagamentoFatura);
		this.dataPagamentoFatura = dataPagamentoFatura;
	}

	public void onSelectDate() {
		System.out.println("onSelectDate: "+dataPagamentoFatura);
	}
}
package br.com.solimar.finan.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaBancaria;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.ContaTipoEnum;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.UtilFile;
import br.com.solimar.finan.view.application.UserSession;
import net.sf.ofx4j.domain.data.MessageSetType;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.banking.BankingResponseMessageSet;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.io.AggregateUnmarshaller;
import net.sf.ofx4j.io.OFXParseException;

@Stateless
public class FileImportRN implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentoRN lancamentoRN;
	
	@Inject
	private ContaBancariaRN contaBancariaRN;
	
	@Inject
	private UserSession userSession;
	
	
	public void importarExtratoBancario(InputStream inputStream) {
		System.out.println("importarExtratoBancario");

		try {

			File fileSource = File.createTempFile("fileSourceOFX", ".ofx");
			File fileTarget = File.createTempFile("fileTargetOFX", ".ofx");

			UtilFile.copyFileUsingStream(inputStream, fileSource);
			UtilFile.changeEncoding(fileSource, "ISO-8859-1", fileTarget, "UTF-8");

			// Verifica se há a indicação de timezone no arquivo
			if (!UtilFile.arquivoPossuiTexto(fileTarget, "[-3:BRT]")) {
				System.out.println("Não tem indicação de time zone");
				// Alterar a time zone para o timezone zero, pois no arquivo não
				// há indicação de timezone nas datas
				// sem isso o ofx4j converte as datas com 3 horas a menos pois
				// essa é a timezone do brasil (-3)
				TimeZone.setDefault(TimeZone.getTimeZone("BRT"));
				// System.out.println("TIME ZONE DEFAULT:
				// "+TimeZone.getDefault());
			}

			
			AggregateUnmarshaller<ResponseEnvelope> a = new AggregateUnmarshaller<ResponseEnvelope>(
					ResponseEnvelope.class);

			ResponseEnvelope re = (ResponseEnvelope) a.unmarshal(new FileInputStream(fileTarget));

			ResponseMessageSet messageSet = re.getMessageSet(MessageSetType.banking);

			if (messageSet != null) {
				List<BankStatementResponseTransaction> bank = ((BankingResponseMessageSet) messageSet)
						.getStatementResponses();

				for (BankStatementResponseTransaction b : bank) {
					
					
					//System.out.println("Código do Banco: " + b.getMessage().getAccount().getBankId());
					//System.out.println("Conta: " + b.getMessage().getAccount().getAccountNumber());
					//System.out.println("Agência: " + b.getMessage().getAccount().getBranchId());
					//System.out.println("Balanço Final: " + b.getMessage().getLedgerBalance().getAmount());
					//System.out.println("Data do Arquivo: " + b.getMessage().getLedgerBalance().getAsOfDate());
					
					List<Transaction> list = b.getMessage().getTransactionList().getTransactions();
					
					
					ContaBancaria contaBancaria = new ContaBancaria();
					contaBancaria.setNome("");
					contaBancaria.setAgenciaNumero(b.getMessage().getAccount().getBranchId());
					contaBancaria.setContaNumero(b.getMessage().getAccount().getAccountNumber());
					contaBancaria.setTipo(ContaTipoEnum.CHECKING_ACCOUNT);
					contaBancaria.setContaApp(userSession.getContaApp());
					
					
					List<ContaBancaria> listaContas = contaBancariaRN.findByAgenciaAndConta(contaBancaria);
					if(listaContas.size() == 0){
						contaBancariaRN.insert(contaBancaria);
					}else{
						contaBancaria = listaContas.get(0);
					}
										

					for (Transaction transaction : list) {
						//System.out.println("TIPO:      " + transaction.getTransactionType().name());
						//System.out.println("ID:        " + transaction.getId());
						//System.out.println("DOCUMENTO: " + transaction.getReferenceNumber());
						//System.out.println("DATA:      " + new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDatePosted()));
						//System.out.println("DATA:      " + transaction.getDatePosted());
						//System.out.println("VALOR:     " + transaction.getBigDecimalAmount());
						//System.out.println("DESCRIÇÃO: " + transaction.getMemo());
						//System.out.println("");
						
						Lancamento lancamento = new Lancamento();
						lancamento.setCategorizado(false);
						lancamento.setContaApp(userSession.getContaApp());
						lancamento.setContaBancaria(contaBancaria);
						lancamento.setData(transaction.getDatePosted());
						lancamento.setDataPagamento(transaction.getDatePosted());
						lancamento.setMemo(transaction.getMemo());
						
						if (transaction.getBigDecimalAmount().signum() == -1) {
							lancamento.setTipo(LancamentoTipoEnum.S);
						}else{
							lancamento.setTipo(LancamentoTipoEnum.E);
						}
						lancamento.setTransactionId(transaction.getId());
						lancamento.setValor(transaction.getBigDecimalAmount());
						
						
						List<Lancamento> listaLancamentos = lancamentoRN.findByMemoAndTransactionIdAndContaApp(lancamento);
						if(listaLancamentos.size() == 0){
							lancamentoRN.insert(lancamento);
						}
						
						
					}
				}
			}

			fileTarget.delete();
			fileSource.delete();

		} catch (OFXParseException | IOException e) {
			e.printStackTrace();
		}

	}

}

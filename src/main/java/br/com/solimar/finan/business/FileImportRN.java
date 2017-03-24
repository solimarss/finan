package br.com.solimar.finan.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.CartaoCredito;
import br.com.solimar.finan.entity.CartaoCreditoFatura;
import br.com.solimar.finan.entity.ContaBancaria;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.ContaTipoEnum;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.GeradorCodigo;
import br.com.solimar.finan.util.UtilFile;
import br.com.solimar.finan.view.application.UserSession;
import net.sf.ofx4j.domain.data.MessageSetType;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.banking.BankingResponseMessageSet;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.creditcard.CreditCardResponseMessageSet;
import net.sf.ofx4j.domain.data.creditcard.CreditCardStatementResponse;
import net.sf.ofx4j.domain.data.creditcard.CreditCardStatementResponseTransaction;
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
	private CartaoCreditoRN cartaoCreditoRN;

	@Inject
	private CartaoCreditoFaturaRN cartaoCreditoFaturaRN;

	@Inject
	private UserSession userSession;

	public void importarCartaoCredito(InputStream inputStream) throws IOException, OFXParseException {
		System.out.println("importarExtratoBancario");

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

		AggregateUnmarshaller<ResponseEnvelope> a = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);

		ResponseEnvelope envelope = (ResponseEnvelope) a.unmarshal(new FileInputStream(fileTarget));

		CreditCardResponseMessageSet messageSet = (CreditCardResponseMessageSet) envelope
				.getMessageSet(MessageSetType.creditcard);

		System.out.println("TIPO DE CARTÃO: " + messageSet.getType());
		System.out
				.println("INSTITUIÇÃO: " + envelope.getSignonResponse().getFinancialInstitution().getOrganization());
		System.out.println("");

		List<CreditCardStatementResponseTransaction> responses = messageSet.getStatementResponses();

		for (CreditCardStatementResponseTransaction response : responses) {

			CreditCardStatementResponse message = response.getMessage();

			System.out.println("NÚMERO DO CARTÃO: " + message.getAccount().getAccountNumber());
			System.out.println("TOTAL: " + message.getLedgerBalance().getAmount());
			System.out.println("DATA DE VENCIMENTO: " + message.getLedgerBalance().getAsOfDate());
			System.out.println("");

			CartaoCredito cartaoCredito = new CartaoCredito();
			cartaoCredito.setNumero(message.getAccount().getAccountNumber());
			cartaoCredito.setContaApp(userSession.getContaApp());
			cartaoCredito.setNome("");
			cartaoCredito.setCreatedAt(new Date());
			cartaoCredito.setUpdatedAt(new Date());
			cartaoCredito.setCodigo(GeradorCodigo.gerar());

			List<CartaoCredito> listaCartoes = cartaoCreditoRN.findByNumero(cartaoCredito);
			if (listaCartoes.size() == 0) {
				cartaoCreditoRN.insert(cartaoCredito);
				System.out.println("==> CARTÃO INSERIDA");

			} else {
				cartaoCredito = listaCartoes.get(0);
				System.out.println("==> CARTÃO NÃO INSERIDA");

			}

			CartaoCreditoFatura fatura = new CartaoCreditoFatura();
			fatura.setCartao(cartaoCredito);
			fatura.setContaApp(userSession.getContaApp());
			fatura.setDataVencimento(message.getLedgerBalance().getAsOfDate());
			fatura.setValor(BigDecimal.valueOf(message.getLedgerBalance().getAmount()));
			fatura.setCreatedAt(new Date());
			fatura.setUpdatedAt(new Date());
			fatura.setCodigo(GeradorCodigo.gerar());

			List<CartaoCreditoFatura> listaFaturas = cartaoCreditoFaturaRN.findByVencimentoAndCartao(fatura);

			if (listaFaturas.size() == 0) {
				cartaoCreditoFaturaRN.insert(fatura);
				System.out.println("==> FATURA INSERIDA");

			} else {
				fatura = listaFaturas.get(0);
				System.out.println("==> FATURA NÃO INSERIDA");

			}

			String currencyCode = message.getCurrencyCode();
			List<Transaction> transactions = message.getTransactionList().getTransactions();

			int qtdRegistros = 0;
			for (Transaction transaction : transactions) {

				System.out.println("ID:    " + transaction.getId());
				System.out.println("TIPO:  " + transaction.getTransactionType());
				System.out.println("DATA:  " + new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDatePosted()));
				System.out.println("MEMO   " + transaction.getMemo());
				System.out.println("VALOR: " + transaction.getAmount() + " " + currencyCode);
				System.out.println("");

				Lancamento lancamento = new Lancamento();
				lancamento.setCategorizado(false);
				lancamento.setContaApp(userSession.getContaApp());
				lancamento.setCartaoCreditoFatura(fatura);
				lancamento.setData(transaction.getDatePosted());
				lancamento.setDataPagamento(fatura.getDataVencimento());
				lancamento.setMemo(transaction.getMemo());
				lancamento.setCreatedAt(new Date());
				lancamento.setUpdatedAt(new Date());
				lancamento.setCodigo(GeradorCodigo.gerar());

				if (transaction.getBigDecimalAmount().signum() == -1) {
					lancamento.setTipo(LancamentoTipoEnum.S);
				} else {
					lancamento.setTipo(LancamentoTipoEnum.E);
				}
				lancamento.setTransactionId(transaction.getId());
				lancamento.setValor(transaction.getBigDecimalAmount());

				List<Lancamento> listaLancamentos = lancamentoRN.findByMemoAndTransactionIdAndContaApp(lancamento);
				if (listaLancamentos.size() == 0) {
					lancamentoRN.insert(lancamento);
					qtdRegistros++;
					System.out.println("==> INSERIDO");
				} else {
					System.out.println("==> NÃO INSERIDO");
				}

				System.out.println("-------------------------------------------------------------- ");

			}

			System.out.println("QUANTIDADE: " + qtdRegistros);
		}

		fileTarget.delete();
		fileSource.delete();

	}

	public void importarExtratoBancario(InputStream inputStream) throws IOException, OFXParseException {
		System.out.println("importarExtratoBancario");

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

		AggregateUnmarshaller<ResponseEnvelope> a = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);

		ResponseEnvelope re = (ResponseEnvelope) a.unmarshal(new FileInputStream(fileTarget));

		ResponseMessageSet messageSet = re.getMessageSet(MessageSetType.banking);

		if (messageSet != null) {
			List<BankStatementResponseTransaction> bank = ((BankingResponseMessageSet) messageSet)
					.getStatementResponses();

			for (BankStatementResponseTransaction b : bank) {

				System.out.println("Código do Banco: " + b.getMessage().getAccount().getBankId());
				System.out.println("Conta: " + b.getMessage().getAccount().getAccountNumber());
				System.out.println("Agência: " + b.getMessage().getAccount().getBranchId());
				System.out.println("Balanço Final: " + b.getMessage().getLedgerBalance().getAmount());
				System.out.println("Data do Arquivo: " + b.getMessage().getLedgerBalance().getAsOfDate());

				List<Transaction> list = b.getMessage().getTransactionList().getTransactions();

				ContaBancaria contaBancaria = new ContaBancaria();
				contaBancaria.setNome("");
				contaBancaria.setBancoCodigo(b.getMessage().getAccount().getBankId());
				contaBancaria.setAgenciaNumero(b.getMessage().getAccount().getBranchId());
				contaBancaria.setContaNumero(b.getMessage().getAccount().getAccountNumber());
				contaBancaria.setTipo(ContaTipoEnum.CHECKING_ACCOUNT);
				contaBancaria.setContaApp(userSession.getContaApp());
				contaBancaria.setCreatedAt(new Date());
				contaBancaria.setUpdatedAt(new Date());
				contaBancaria.setCodigo(GeradorCodigo.gerar());
				contaBancaria.setLancamentoManual(false);

				List<ContaBancaria> listaContas = contaBancariaRN.findByAgenciaAndConta(contaBancaria);
				if (listaContas.size() == 0) {
					contaBancariaRN.insert(contaBancaria);
					System.out.println("==> CONTA INSERIDA");

				} else {
					contaBancaria = listaContas.get(0);

					System.out.println("==> CONTA NÃO INSERIDA");

				}
				System.out.println("--------------------------------------------------");

				int qtdRegistros = 0;

				for (Transaction transaction : list) {
					System.out.println("TIPO:      " + transaction.getTransactionType().name());
					System.out.println("ID:        " + transaction.getId());
					System.out.println("DOCUMENTO: " + transaction.getReferenceNumber());
					System.out.println(
							"DATA:      " + new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDatePosted()));
					System.out.println("DATA:      " + transaction.getDatePosted());
					System.out.println("VALOR:     " + transaction.getBigDecimalAmount());
					System.out.println("DESCRIÇÃO: " + transaction.getMemo());

					Lancamento lancamento = new Lancamento();
					lancamento.setCategorizado(false);
					lancamento.setContaApp(userSession.getContaApp());
					lancamento.setContaBancaria(contaBancaria);
					lancamento.setData(transaction.getDatePosted());
					lancamento.setDataPagamento(transaction.getDatePosted());
					lancamento.setMemo(transaction.getMemo());
					lancamento.setCreatedAt(new Date());
					lancamento.setUpdatedAt(new Date());
					lancamento.setCodigo(GeradorCodigo.gerar());

					if (transaction.getBigDecimalAmount().signum() == -1) {
						lancamento.setTipo(LancamentoTipoEnum.S);
					} else {
						lancamento.setTipo(LancamentoTipoEnum.E);
					}
					lancamento.setTransactionId(transaction.getId());
					lancamento.setValor(transaction.getBigDecimalAmount());

					List<Lancamento> listaLancamentos = lancamentoRN.findByMemoAndTransactionIdAndContaApp(lancamento);
					if (listaLancamentos.size() == 0) {
						lancamentoRN.insert(lancamento);
						qtdRegistros++;
						System.out.println("==> INSERIDO");
					} else {
						System.out.println("==> NÃO INSERIDO");
					}

					System.out.println("-------------------------------------------------------------- ");

				}

				System.out.println("QUANTIDADE: " + qtdRegistros);
			}
		}

		fileTarget.delete();
		fileSource.delete();

	}

}

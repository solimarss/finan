package br.com.solimar.finan.business;

import java.util.Date;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.util.DataUtil;

public class PeriodoRN {

	public static Date getFirstDayOfThePeriod(ContaApp contaApp, int mes, int ano) {

		if (contaApp.getUseStartDay()) {

			return DataUtil.getDate(contaApp.getStartDay(), mes, ano);

		}

		return DataUtil.getFirstDayOfTheMonth(mes, ano);
	}

	public static Date getLastDayOfThePeriod(ContaApp contaApp, int mes, int ano) {

		if (contaApp.getUseStartDay()) {

			return DataUtil.getDate(contaApp.getStartDay()-1, mes + 1, ano);

		}

		return DataUtil.getLastDayOfTheMonth(mes, ano);
	}

}

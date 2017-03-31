package br.com.solimar.finan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {

	public static int getCurrentMonth() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		int mes = (cal.get(Calendar.MONDAY) + 1);
		return mes;

	}

	public static int getCurrentYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		int ano = cal.get(Calendar.YEAR);
		return ano;

	}

	public static Date getFirstDayOfTheMonth() {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		int dia = 1;
		int mes = (cal.get(Calendar.MONDAY) + 1);
		int ano = cal.get(Calendar.YEAR);

		System.out.println(dia + "/" + mes + "/" + ano);

		try {
			Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
			return data;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getFirstDayOfTheMonth(int mes, int ano) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		int dia = 1;
		int mesNum = mes;
		int anoNum = ano;

		System.out.println(dia + "/" + mesNum + "/" + anoNum);

		try {
			Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mesNum + "/" + anoNum);
			return data;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getLastDayOfTheMonth() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mes = (cal.get(Calendar.MONDAY) + 1);
		int ano = cal.get(Calendar.YEAR);

		System.out.println(dia + "/" + mes + "/" + ano);

		try {
			Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);

			return data;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getLastDayOfTheMonth(int mes, int ano) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mesNum = mes;
		int anoNum = ano;

		System.out.println(dia + "/" + mesNum + "/" + anoNum);

		try {
			Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mesNum + "/" + anoNum);

			return data;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}

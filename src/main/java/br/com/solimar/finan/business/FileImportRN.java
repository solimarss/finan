package br.com.solimar.finan.business;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ejb.Stateless;

@Stateless
public class FileImportRN {
	
	private static InputStreamReader r;
	
	public void importarExtratoBancario(InputStream inputStream) {
		System.out.println("importarExtratoBancario");
		
		r = new InputStreamReader(inputStream);
		
	}

}

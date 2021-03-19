package br.com.bradseg.sise.apolicevida.utils.texto;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringsUtils {


	public static String buildStr(final String... strings){
		StringBuilder sb = new StringBuilder();
		for(String s : strings){
			sb.append(s);
		}

		return sb.toString();    
	}

	public static StringBuffer buildStringBuffer(final String... strings){
		StringBuffer sb = new StringBuffer();
		for(String s : strings){
			sb.append(s);
		}

		return sb;    
	}

	public static String replaceFor(final String orig, final Object o, final String token) {
		StringBuffer sb = new StringBuffer();
		if (o == null) {
			String out = orig.replace(token, " null ");
			return out;
		}
		if (o instanceof String) {

			sb.append('\'');
			sb.append((String) o);
			sb.append('\'');

			String out = orig.replace(token,sb.toString());
			return out;
		}
		if (o instanceof Integer || o instanceof Long || o instanceof Double) {

			sb.append( o);


			String out = orig.replace(token, sb);
			return out;
		}
		if(o instanceof Date){
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String data = format.format((Date)o);
			
			sb.append('\'');
			sb.append(data);
			sb.append('\'');

			String out = orig.replace(token,sb.toString());
			return out;
		}

		return orig;

	}

	public static StringBuffer replaceFor(final StringBuffer orig, final Object o, final String token) {
		if(orig.indexOf(token)==-1) {
			return orig;
		}
		StringBuffer sb = new StringBuffer();
		if (o == null) {
			StringBuffer out = orig.replace(orig.indexOf(token),token.length(), " null ");
			return out;
		}
		if (o instanceof String) {

			sb.append('\'');
			sb.append((String) o);
			sb.append('\'');

			StringBuffer out = orig.replace(orig.indexOf(token),orig.indexOf(token)+token.length(),sb.toString());
			return out;
		}
		if (o instanceof Integer || o instanceof Long || o instanceof Double) {

			sb.append( o);


			StringBuffer out = orig.replace(orig.indexOf(token),orig.indexOf(token)+token.length(), sb.toString());
			return out;
		}
		
		if(o instanceof Date){
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String data = format.format((Date)o);

			sb.append('\'');
			sb.append(data);
			sb.append('\'');

			StringBuffer out = orig.replace(orig.indexOf(token),orig.indexOf(token)+token.length(),sb.toString());
			return out;
		}

		return orig;

	}

	public static String checkString(final String texto, final Integer qtde){
		String retorno = new String();
		if (texto != null){
			final int tamTexto = texto.trim().length();
			retorno = tamTexto>qtde?texto.trim().substring(0, qtde):texto.trim();
		}
		return retorno;
	}

	public static String removerAcentos(final String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String replicarCaracter(final String str,  final Integer size, final String character, final boolean replicarLeft) {
		StringBuilder sb = new StringBuilder();
		final int length = size - str.length();
		
		if(replicarLeft) {
			for (int i = 0; i< length; i++) {
				sb.append(character);
			}
		sb.append(str);
		} else {
			sb.append(str);
			for (int i = 0; i< length; i++) {
				sb.append(character);
			}
		}
	    return sb.toString();
	}

	
}

/**
* 
* as linhas dos arquivos são colocadas em uma entidade que identifica o numero da linha e o nome do arquivo origem
* @author  Renato Tomaz Nati
* @version 1.0
* @since   2019-03-06
*/
package br.com.bradseg.sise.apolicevida.utils.file;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;




public class File2Lines {
	private ArrayList<LineEnt> lines ;
	public File2Lines(final ArrayList<LineEnt> lines) {
		this.setLines(lines);
	}
	private void realizaLeitura(final String filepath) {
		String content="";
		try {
			content = IOUtils.toString(new BufferedReader(new FileReader(filepath)));
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String asString ="";
		
		//	asString= new String(content.getBytes(), "UnicodeLittleUnmarked");//for text file
			try {
				asString= new String(content.getBytes(), "UTF8");
			} catch (UnsupportedEncodingException e) {
			
				e.printStackTrace();
			}//for text file
			
		
		
		try {
			
			byte[] newBytes = asString.getBytes("UTF8");
			content = new String(newBytes);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
			String[] lines = content.split("\n");
		
			this.setLines(new ArrayList<LineEnt>(30));
			File f = new File(filepath);
			for(int i  = 0 ; i< lines.length;i++){
				LineEnt le = new LineEnt();
				le.setFilename(f.getName());
		
			le.setLinenumber(i);
			le.setConteudo(lines[i]);
			this.getLines().add(le);
			}
	}
	public File2Lines(final String filepath) throws FileNotFoundException, IOException{
		realizaLeitura( filepath) ;
	}
	public Iterator<LineEnt> getIteratorLinhas(){
		return getLines().iterator();
	}
	public ArrayList<LineEnt> getLines() {
		return lines;
	}
	private void setLines(ArrayList<LineEnt> lines) {
		this.lines = lines;
	}

}

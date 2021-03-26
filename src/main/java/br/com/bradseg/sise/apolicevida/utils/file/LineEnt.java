/**
 *
 * @author renat
 *
 * @version 1.0
 */
package br.com.bradseg.sise.apolicevida.utils.file;

/**
* lineEnt é uma entidade que identifica arquivo origem com a linha do arquivo e o conteudo
* @author  Renato Tomaz Nati
* @version 1.0
* @since   2019-03-06
*/

public class LineEnt {
	private Integer linenumber;
	private String filename;
	private String conteudo;
	/**
	 * @return the linenumber
	 */
	public Integer getLinenumber() {
		return linenumber;
	}
	/**
	 * @param linenumber the linenumber to set
	 */
	public void setLinenumber(final Integer linenumber) {
		this.linenumber = linenumber;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(final String filename) {
		this.filename = filename;
	}
	/**
	 * @return the line
	 */
	public String getConteudo() {
		return conteudo;
	}
	/**
	 * @param conteudo: line the line to set
	 */
	public void setConteudo(final String conteudo) {
		this.conteudo = conteudo;
	}
	
}

package br.com.ifsp.opendata.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String nome;
	private Date data;
	private String frequencia;
	private String cidade;
	private String estado;
	private Date dataDeUpload;
	private String usuarioResponsavel;
	private String nomeDoArquivoPDF;
	private String nomeDoArquivoCSV;
	private String url;
	private String tamanho;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getDataDeUpload() {
		return dataDeUpload;
	}
	public void setDataDeUpload(Date dataDeUpload) {
		this.dataDeUpload = dataDeUpload;
	}
	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}
	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}
	public String getNomeDoArquivoPDF() {
		return nomeDoArquivoPDF;
	}
	public void setNomeDoArquivoPDF(String nomeDoArquivoPDF) {
		this.nomeDoArquivoPDF = nomeDoArquivoPDF;
	}
	public String getNomeDoArquivoCSV() {
		return nomeDoArquivoCSV;
	}
	public void setNomeDoArquivoCSV(String nomeDoArquivoCSV) {
		this.nomeDoArquivoCSV = nomeDoArquivoCSV;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
}

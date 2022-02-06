package it.contatto.entity;

public class ContoCorrente {
	
	private String intestatario;
	private String iban;
	private double saldo;
	private String dataCreazione;
	
	
	public String getIntestatario() {
		return intestatario;
	}
	public String getIban() {
		return iban;
	}
	public double getSaldo() {
		return saldo;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

}

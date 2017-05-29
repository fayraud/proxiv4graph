package com.huios.metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("prototype")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idTransaction;
	private String nom;
	private double montant;
	public long getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(long idTransaction) {
		this.idTransaction = idTransaction;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	@Override
	public String toString() {
		return "Transaction [idTransaction=" + idTransaction + ", nom=" + nom + ", montant=" + montant + "]";
	}
	
	

}

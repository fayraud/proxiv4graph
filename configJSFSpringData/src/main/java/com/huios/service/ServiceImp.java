package com.huios.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.huios.dao.springdata.IDaoConseiller;
import com.huios.dao.springdata.IDaoGerant;
import com.huios.dao.springdata.IDaoTransaction;
import com.huios.dao.springdata.IDaoAdresse;
import com.huios.dao.springdata.IDaoClient;
import com.huios.dao.springdata.IDaoCompte;
import com.huios.metier.Adresse;
import com.huios.metier.Client;
import com.huios.metier.Compte;
import com.huios.metier.Conseiller;
import com.huios.metier.Gerant;
import com.huios.metier.Personne;
import com.huios.metier.Transaction;
import com.huios.metier.User;
import com.huios.metier.CompteCourant;
import com.huios.metier.CompteEpargne;

@Service
public class ServiceImp implements IServiceConseiller, IServiceGerant {

	
	@Autowired
	private IDaoClient daoP;
	
	@Autowired
	private IDaoGerant daoG;
	
	@Autowired
	private IDaoAdresse daoA;
	
	@Autowired
	private IDaoConseiller daoC;
	
	
	@Autowired
	private IDaoCompte daoCo;
	@Autowired
	private Collection<Compte> comptes;
	@Autowired
	private Collection<CompteCourant> comptesCourant;
	@Autowired
	private Collection<CompteEpargne> comptesEpargne;
	@Autowired
	private IDaoTransaction daoT;

	
	
	
	public IDaoClient getDaoP() {
		return daoP;
	}

	public void setDaoP(IDaoClient daoP) {
		this.daoP = daoP;
	}

	public IDaoGerant getDaoG() {
		return daoG;
	}

	public void setDaoG(IDaoGerant daoG) {
		this.daoG = daoG;
	}

	public IDaoAdresse getDaoA() {
		return daoA;
	}

	public void setDaoA(IDaoAdresse daoA) {
		this.daoA = daoA;
	}

	public IDaoConseiller getDaoC() {
		return daoC;
	}

	public void setDaoC(IDaoConseiller daoC) {
		this.daoC = daoC;
	}

	public IDaoCompte getDaoCo() {
		return daoCo;
	}

	public void setDaoCo(IDaoCompte daoCo) {
		this.daoCo = daoCo;
	}

	public Collection<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}

	public Collection<CompteCourant> getComptesCourant() {
		return comptesCourant;
	}

	public void setComptesCourant(Collection<CompteCourant> comptesCourant) {
		this.comptesCourant = comptesCourant;
	}

	public Collection<CompteEpargne> getComptesEpargne() {
		return comptesEpargne;
	}

	public void setComptesEpargne(Collection<CompteEpargne> comptesEpargne) {
		this.comptesEpargne = comptesEpargne;
	}

	public IDaoTransaction getDaoT() {
		return daoT;
	}

	public void setDaoT(IDaoTransaction daoT) {
		this.daoT = daoT;
	}

	@Override
	public Conseiller verificationLogin(String login, String pwd) {
		return daoC.verificationLogin(login, pwd);
	}

	@Override
	public Gerant verificationLoginGerant(String login, String pwd) {
		return daoG.verificationLoginGerant(login, pwd);
	}
	
	@Override
	public Collection<Client> listerClientsParConseiller(Conseiller conseiller) {
		return daoP.listerClientsParConseiller(conseiller);
	}


	@Override
	public Collection<Compte> listerComptesClient(Client client) {
		return daoCo.listerComptesClient(client);
	}

	@Override
	public Client retourneClientParId(long idClient) {
		return daoP.findOne(idClient);
	}
	@Override
	public Compte getCompteParId(long idCompte) {
		return daoCo.findOne(idCompte);
	}

	@Override
	public Collection<Compte> listerAutresComptes(long idCompte) {
		return daoCo.listerAutresComptes(idCompte);
	}

	@Override
	public boolean virementComptes(Compte compteDebiteur, Compte compteCrediteur, double montant) {
		if (montant >= 0) {
			if (compteDebiteur instanceof CompteEpargne) {
				if (montant > compteDebiteur.getSolde()) {
					return false;
				}
			}
			if (compteDebiteur instanceof CompteCourant) {
				double decouvert = ((CompteCourant) compteDebiteur).getDecouvert();
				if (montant > compteDebiteur.getSolde() + decouvert) {
					return false;
				}
			}
			compteDebiteur.setSolde(compteDebiteur.getSolde() - montant);
			compteCrediteur.setSolde(compteCrediteur.getSolde() + montant);
			
			daoCo.save(compteDebiteur);
			daoCo.save(compteCrediteur);
			Transaction transaction = new Transaction();
			transaction.setNom(compteDebiteur.getClient().getConseiller().getNom());
			transaction.setMontant(montant);
			daoT.save(transaction);
		
			
			
			return true;
		}
		return false;
	}
		
	

	@Override
	public Conseiller afficherConseiller(long idConseiller) {
		
		return (Conseiller) daoC.findOne(idConseiller);
	}

	@Override
	public void modifierClient(Client client) {
		daoP.save(client);
		daoA.save(client.getAdresse());
	}

	@Override
	public void ajouterClient(long idConseiller, Client client) {
		Conseiller cons = daoC.findOne(idConseiller);
		//Collection<Client> clients = daoP.listerClientsParConseiller(cons);
		
		//if(clients.size()>=10){
			client.setConseiller(cons);
			
			daoP.save(client);
					
		//}
			
		
		
	}

	@Override
	public void ajouterConseiller(long idPersonne, Conseiller conseiller) {
		// TODO Auto-generated method stub
		Gerant gerant=daoG.findOne(idPersonne);
		conseiller.setGerant(gerant);
		daoC.save(conseiller);
		
	}

	@Override
	public void ajouterGerant(Gerant gerant) {
		daoG.save(gerant);
		
	}

	@Override
	public void ajouterCompte(long idPersonne, Compte compte) {
		Client client=daoP.findOne(idPersonne);
		compte.setClient(client);
		daoCo.save(compte);
		
	}

	@Override
	public Collection<Conseiller> listerConseillerParGerant(Gerant gerant) {
		
		return daoG.listerConseillerParGerant(gerant);
	}

	@Override
	public void ajouterAdresse(long idPersonne, Adresse adresse) {
		Client client=daoP.findOne(idPersonne);
		client.setAdresse(adresse);
		daoA.save(adresse);
		
		
	}

	@Override
	public Collection<CompteCourant> listerComptesCourantClient(Client client) {
		comptes.clear();
		comptes = daoCo.listerComptesClient(client);
		comptesCourant= new ArrayList<>();
		for (Compte c : comptes){
		if (c instanceof CompteCourant){
			comptesCourant.add((CompteCourant)c);
		}
		}
		return comptesCourant;
	}

	@Override
	public Collection<CompteEpargne> listerComptesEpargneClient(Client client) {
		comptes.clear();
		comptes = daoCo.listerComptesClient(client);
		comptesEpargne= new ArrayList<>();
		for (Compte c : comptes){
		if (c instanceof CompteEpargne){
			comptesEpargne.add((CompteEpargne)c);
		}
		}
		return comptesEpargne;
	}

	@Override
	public void modifierConseiller(Conseiller conseiller) {
		daoC.save(conseiller);
		daoA.save(conseiller.getAdresse());
	}

	@Override
	public Collection<Compte> listeCompteDecouvert(Gerant gerant) {
//		System.out.println(gerant);
		Collection<Conseiller> conseillers= daoG.listerConseillerParGerant(gerant);
		Collection<Compte> comptes = new ArrayList<>();
		for (Conseiller co : conseillers){
			Collection<Client> clients = new ArrayList<>();
			clients = listerClientsParConseiller(co);
			for(Client cl : clients ){
				Collection<Compte> comptescl = new ArrayList<>();
				comptescl = listerComptesClient(cl);
				for(Compte c : comptescl){
					if(c.getSolde()<0){
						comptes.add(c);
					}
				}
			}
			
		}
		return comptes;
	}

	
	@Override
	public void supprimerCompteCourant(CompteCourant compteCourant) {
		daoCo.delete(compteCourant);
		
	}
	
	@Override
	public void supprimerCompteEpargne(CompteEpargne compteEpargne) {
		daoCo.delete(compteEpargne);
		
	}

	@Override
	public int nombreTransactionParConseiller(String nom) {
		daoT.nombreTransactionParConseiller(nom);
		return 0;
	}


	
	
	
}

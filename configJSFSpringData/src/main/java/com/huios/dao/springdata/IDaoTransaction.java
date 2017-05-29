package com.huios.dao.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.huios.metier.Transaction;

public interface IDaoTransaction extends JpaRepository<Transaction,Long>{
	
	@Query("select Count(t)  from Transaction t where t.nom = :nom")
	public int nombreTransactionParConseiller(@Param("nom")String nom);

}

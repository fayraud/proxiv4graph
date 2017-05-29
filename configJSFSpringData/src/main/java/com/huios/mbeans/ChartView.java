package com.huios.mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.huios.metier.Conseiller;
import com.huios.metier.Gerant;
import com.huios.service.IServiceGerant;
 
@Controller
public class ChartView implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 6486362711032672304L;
	
	private PieChartModel pieModel1;
    private PieChartModel pieModel2;

    
    @Autowired
    private IServiceGerant daoG; 
    
    
    public void init(Gerant gerant) {
    	 System.out.println(gerant);
        createPieModels(gerant);
        System.out.println(gerant);
    }
    
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
     
    public PieChartModel getPieModel2() {
        return pieModel2;
    }
     
    private void createPieModels(Gerant gerant) {
    	
        createPieModel1(gerant);
//        createPieModel2();
    }
 
    private String createPieModel1(Gerant gerant) {
        pieModel1 = new PieChartModel();
        int i=1;
        Collection<Conseiller> conseillers = new ArrayList<Conseiller>();
        System.out.println(gerant);
        System.out.println(gerant.getConseillers());
        		conseillers=gerant.getConseillers();
        		System.out.println(conseillers);
       for (Conseiller c : conseillers){
    	   System.out.println(i);
    	   pieModel1.set("Conseiller "+i, daoG.nombreTransactionParConseiller(c.getNom()));
    	   i=i+1;
    	   
       }
//        pieModel1.set("Conseiller 1", 2);
//        pieModel1.set("Conseiller 2", 325);
//        pieModel1.set("Conseiller 3", 702);
//        pieModel1.set("Conseiller 4", 421);
         
        pieModel1.setTitle("Graphique de performance des conseillers");
        pieModel1.setLegendPosition("w");
        return "pie.xhtml";
    }
     
    private void createPieModel2() {
        pieModel2 = new PieChartModel();
         
        pieModel2.set("Conseiller 1", 540);
        pieModel2.set("Conseiller 2", 325);
        pieModel2.set("Conseiller 3", 702);
        pieModel2.set("Conseiller 4", 421);
         
        pieModel2.setTitle("graph exprimé en pourcentage");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }
     
}
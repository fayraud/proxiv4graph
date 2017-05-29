import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.huios.metier.Client;
import com.huios.metier.Compte;
import com.huios.service.IServiceConseiller;
import com.huios.service.ServiceImp;

public class Lanceur {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IServiceConseiller sc = new ServiceImp();
				Client c =new Client();
				c=sc.retourneClientParId(2);
				System.out.println(c);
				Collection<Compte> cs = new ArrayList<Compte>();
				cs=sc.listerComptesClient(c);
				System.out.println(cs);

	}
	
	

}

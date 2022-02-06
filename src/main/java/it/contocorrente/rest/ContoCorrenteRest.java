package it.contocorrente.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.contatto.entity.ContoCorrente;
import it.contatto.entity.Movimento;
import it.contatto.entity.Operazione;

@Path("/conto")
public class ContoCorrenteRest {

	public static ArrayList<ContoCorrente> conti = new ArrayList<>();

	public static ArrayList<Movimento> movimenti = new ArrayList<>();
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response creaConto(ContoCorrente c) {
		for(ContoCorrente cc : conti) {
			if(cc.getIban().equals(c.getIban())) {
				return Response.status(404).entity("Conto già esistente").build();
			}
		}
		conti.add(c);


		return Response.status(200).entity("Conto creato con successo").build();
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response eliminaConto(ContoCorrente c) {
		for(ContoCorrente cc : conti) {
			if(cc.getIban().equals(c.getIban())) {
				conti.remove(cc);
				return Response.status(200).entity("Conto cancellato con successo").build();

			}

		}


		return Response.status(404).entity("Conto non trovato").build();

	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response aggiornaConto(ContoCorrente c) {
		for(ContoCorrente cc : conti) {
			if(cc.getIban().equals(c.getIban())) {
				int index = conti.lastIndexOf(cc);
				conti.set(index, c);
				return Response.status(200).entity("Conto modificato con successo").build();
			}

		}

		return Response.status(404).entity("Conto non trovato").build();

	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movimento> getMovimento() {
		return movimenti;

	}
	
	@PUT
	@Path("/movimento")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response movimento(Movimento m) {
		for(ContoCorrente cc : conti) {
			if(cc.getIban().equals(m.getIban())) {
				if(m.getTipo().equals(Operazione.PRELIEVO)) {
					if(m.getImporto() > cc.getSaldo() && m.getImporto() < 0) {
						return Response.status(406).entity("Prelievo non disponibile").build();
						
					}
					double saldo = cc.getSaldo() - m.getImporto();
					cc.setSaldo(saldo);
					movimenti.add(m);
					return Response.status(200).entity("Operazione effettuata con successo: " + saldo).build();
					
				}
				if(m.getTipo().equals(Operazione.VERSAMENTO)) {
					if(m.getImporto() < 0) {
						return Response.status(406).entity("Versamento non disponibile").build();
					}
					double nuovoSaldo = cc.getSaldo() + m.getImporto();
					cc.setSaldo(nuovoSaldo);
					movimenti.add(m);
					return Response.status(200).entity("Versamento effettuato: " + nuovoSaldo).build();
				}
			}
			
		}
		return Response.status(406).entity("Operazione non effettuata").build();
		
	}
	
}

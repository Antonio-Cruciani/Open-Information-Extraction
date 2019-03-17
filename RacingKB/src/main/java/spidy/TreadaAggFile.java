package spidy;

import aggiornamento.ControlloreAggiornamenti;


public class TreadaAggFile implements Runnable {
	  public void run() {
		  try {
			ScriviJson.aggiorna("Pages/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}

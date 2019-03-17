package spidy;

import aggiornamento.ControlloreAggiornamenti;



public class ThreadSpiderAvv implements Runnable {
	  public void run() {
		  try {
			  SpiderInitialize.SpiderInitialization();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}
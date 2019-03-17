package aggiornamento;



public class ThreadAggkb implements Runnable {
	  public void run() {
		  try {
			ControlloreAggiornamenti.controlloaggiornamenti();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}

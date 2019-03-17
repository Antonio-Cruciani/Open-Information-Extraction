package estrattore;

import spidy.SpiderInitialize;

public class ThreadExtraction implements Runnable {
	public void run() {
		  try {
			  RunExtraction.AvvioEstrazione();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}

package racing;



class MyThread implements Runnable {
String name;
Thread t;
    MyThread (String thread){
    name = thread; 
    t = new Thread(this, name);
System.out.println("New thread: " + t);
t.start();
}
public void run() {
 
	
//SCRIVERE QUI TUTTO IL CODICE DI OGNI CLASSE: TRIPLE EXTRACT; CRAWLER; CRAWLER AGGIORNANTE
}
}

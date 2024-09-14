import java.awt.EventQueue;

import br.ufc.poo.grafica.frame.*;

public class Main {

	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MeuMercado frame = new MeuMercado();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}

}
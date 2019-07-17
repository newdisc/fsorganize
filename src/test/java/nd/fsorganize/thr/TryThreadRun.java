package nd.fsorganize.thr;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TExt extends Thread {
	@Override
	public void run() {
		log.info("TExt Thread started");
		try {
			Thread ct = Thread.currentThread();
			/*
			*/
			synchronized(ct) {
				ct.notify();
			}
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			final String err = "TExt Thread interrupted";
			log.error(err);
		}
		log.info("TExt thread ending");
	}
}

@Slf4j
public class TryThreadRun {
	public static void main(final String[] args) throws InterruptedException {
		log.info("Starting main");
		final TExt r = new TExt();
		//final Thread t = new Thread(r,"Rable-1");
		r.start();
		log.info("Main Thread Started");
		//r.interrupt();
		/*
		synchronized(t) {
			t.wait();
		}
		*/
		log.info("Main Thread interrupts TExt");
		r.join();
		log.info("Main Thread completed");
	}
}

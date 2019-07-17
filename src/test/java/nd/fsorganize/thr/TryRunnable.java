package nd.fsorganize.thr;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Rable implements Runnable {
	@Override
	public void run() {
		log.info("Rable Thread started");
		try {
			Thread ct = Thread.currentThread();
			/*
			*/
			synchronized(ct) {
				ct.notify();
			}
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			final String err = "Thread interrupted";
			log.error(err);
		}
		log.info("Rable thread ending");
	}
}

@Slf4j
public class TryRunnable {
	public static void main(final String[] args) throws InterruptedException {
		log.info("Starting main");
		final Rable r = new Rable();
		final Thread t = new Thread(r,"Rable-1");
		t.start();
		log.info("Thread Started");
		t.interrupt();
		/*
		synchronized(t) {
			t.wait();
		}
		*/
		log.info("Thread completed");
	}
}

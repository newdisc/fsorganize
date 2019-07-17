package nd.fsorganize.thr;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

class Cable implements Callable<Long> {
	@Override
	public Long call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

@Slf4j
public class TryCallable {
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

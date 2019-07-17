package nd.fsorganize.thr;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TryExecutor {
	public static class Runner implements Runnable {
		final BlockingQueue<Integer> queue;
		public Runner(BlockingQueue<Integer> q) {
			queue = q;
		}
		@Override
		public void run() {
			log.info("Started runnable");
			try {
				for ( int i = 0; i < 5; i++ ) { 
					log.info("Received Number: " + queue.take());
					intrSleep(4);
				}
			} catch (InterruptedException e) {
				log.error("Interrupted while taking");
			}
			intrSleep(3);
			log.info("Ended runnable");
		}

		public static void intrSleep(int nSec) {
			try {
				TimeUnit.SECONDS.sleep(nSec);
			} catch (InterruptedException e) {
				log.error("Interrupted runnable");
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Executor exec = Executors.newFixedThreadPool(3);//Executors.newCachedThreadPool();
		final BlockingQueue<Integer> qu = new LinkedBlockingQueue<Integer>(3);
		qu.put(5);
		qu.put(4);

		ThreadPoolExecutor tp = (ThreadPoolExecutor) exec;
		tp.execute(new Runner(qu));
		tp.execute(new Runner(qu));
		qu.put(15);
		qu.put(14);
		Runner.intrSleep(2);
		qu.put(25);
		qu.put(24);
		log.info("Threads in pool: " +tp.getPoolSize());
		tp.execute(new Runner(qu));
		tp.execute(new Runner(qu));
		log.info("Threads in pool: " +tp.getPoolSize());
		qu.put(35);
		qu.put(34);
		qu.put(33);
		qu.put(32);
		qu.put(31);
		tp.shutdown();
		tp.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		log.info("Finished main");
	}
}

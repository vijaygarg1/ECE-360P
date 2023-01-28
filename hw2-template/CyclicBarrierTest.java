public class CyclicBarrierTest implements Runnable {
	final static int numParties = 5;
	static int waitCount = 0;
	static int leaveCount = 0;
	static boolean barrierBroken = false;

	final CyclicBarrier barrier;
	int numRounds;

	public static void reset() {
		waitCount = -1;
		leaveCount = -1;
		barrierBroken = false;
	}

	public CyclicBarrierTest(CyclicBarrier barrier, int numRounds) {
		this.barrier = barrier;
		this.numRounds = numRounds;
	}

	public void run() {
		int index = -1;

		for (int round = 0; round < numRounds; ++round) {
			synchronized(CyclicBarrier.class) {
				if(round<waitCount)
					barrierBroken = true;
				waitCount = Integer.max(waitCount, round);
			}
			// System.out.println("Thread " + Thread.currentThread().getId() + " is waiting round:" + round);
			try {
				index = barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(CyclicBarrier.class) {
				if(round<leaveCount)
					barrierBroken = true;
				leaveCount = Integer.max(leaveCount, round);
			}
			// System.out.println("Thread " + Thread.currentThread().getId() + " is leaving round:" + round);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		int numRounds = 20;

		CyclicBarrier barrier;
		// if (args[0].startsWith("s"))
		// 	barrier = new SemaphoreCyclicBarrier(numParties);
		// else
			barrier = new MonitorCyclicBarrier(numParties);

		Thread[] t = new Thread[numParties];



		
		/*
		 * 1. ACTIVE BARRIER
		 * Many threads run in parallel
		 * Each thread calls barrier.await() 'numRounds' number of times
		 * If the running threads block - there's an issue with the behavior of the barrier
		 */
		reset();
		for (int i = 0; i < numParties; ++i) {
			t[i] = new Thread(new CyclicBarrierTest(barrier, numRounds));
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].start();
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].join();
		}
		if(barrierBroken) {
			System.out.println("Test 1 Failed - Barrier was broken during execution");
		}
		else {
			System.out.println("Test 1 Succeeded!");
		}



		/*
		 * 2. DEACTIVATED BARRIER
		 * The barrier is deactivated
		 * Many threads run in parallel 
		 * Each thread iterates and calls barrier.await() a distinct number of times. 
		 * With an active barrier - the running threads will wait forever since not enough threads can reach the barrier in later rounds
		 * With a deactivated barrier - this should work fine
		 */
		barrier.deactivate();
		for (int i = 0; i < numParties; ++i) {
			t[i] = new Thread(new CyclicBarrierTest(barrier, i));
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].start();
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].join();
		}
		System.out.println("Test 2 Succeeded!");



		/* 
		 * 3. MIDWAY BARRIER DEACTIVATION
		 * The barrier is reactivated.
		 * Many threads run in parallel 
		 * Each thread iterates and calls barrier.await() a distinct number of times - with an active barrier the threads would block forever. 
		 * Before waiting for the threads to complete - the barrier is deactivated
		 * Deactivation should 
		 * 		a. nullify the barrier, 
		 * 		b. release any waiting threads
		 * If the barrier is deactivated as expected - the threads should be able to execute to completion
		 */
		barrier.activate();
		for (int i = 0; i < numParties; ++i) {
			t[i] = new Thread(new CyclicBarrierTest(barrier, i));
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].start();
		}
		barrier.deactivate();
		for (int i = 0; i < numParties; ++i) {
			t[i].join();
		}
		System.out.println("Test 3 Succeeded!");




		/*
		 * 4. ACTIVE BARRIER 2.0
		 * This is identical to the first test case above
		 * This serves as a check to ensure that deactivating the barrier in the middle of its execution in case 3 did not break it in any way
		 */
		reset();
		barrier.activate();
		for (int i = 0; i < numParties; ++i) {
			t[i] = new Thread(new CyclicBarrierTest(barrier, numRounds));
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].start();
		}
		for (int i = 0; i < numParties; ++i) {
			t[i].join();
		}
		if(barrierBroken) {
			System.out.println("Test 4 Failed - Barrier was broken during execution");
		}
		else {
			System.out.println("Test 4 Succeeded!");
		}
	}
}
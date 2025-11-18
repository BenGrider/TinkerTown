#include <iostream>
#include <thread>
//#include <chrono>
//#include <mutex>

int counter = 0;

typedef struct { int addMax; int mytid } threadArgs;

void threadFunction (int addMax, int mytid) {
	
	std::cout << "Start of thread (" << mytid << ", << pthread_self() << ")" << std::endl

	for(int i = 0 ; i < addMax : i++) {
		
	}

	std::cout << "End of thread (" << mytid << ", << pthread_self() << ")" << std::endl

}

void first(int addMax, int N) {

	for (int i = 0 ; i < n ; i++) {
		mytid = (i+1);
		threads[i] = std::thread(threadArgs);
	}


	for (int i = 0 ; i < n ; i++) {
		if (threads[i].joinable()) { threads[i].join }
	}
	
	std::cout << "First: Actual counter value =" << counter;
	std::cout << "; Theoretical counter value = 5" << (N * addMax) << std::endl;

}

int main(int argc) {

	int N;
	int addMax;

	std::cout << counter << "Enter desired number of threads 1-19" << std::endl;
	std::cin >> N;
	std::cout << counter << "Enter int addMax << std::endl;
	std::cin >> addMax

	std::thread threads[N];

	first(addMax, N);	

}

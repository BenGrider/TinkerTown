#include <iostream>
#include <semaphore>
#include <thread>

int counter = 0;
std::counting_semaphore S{2};

typedef struct { int addMax; int mytid; } threadArgs;

void threadFunction(threadArgs args) {
    S.acquire();
    std::cout << "Start of thread (" << args.mytid << ", " << std::this_thread::get_id() << ") " << std::endl;
    S.release();
    for(int i = 0 ; i < args.addMax; i++) {
        ++counter;
    }

    std::cout << "End of thread (" << args.mytid << ", " << std::this_thread::get_id() << ")" << std::endl;

}

void threadFunction2(threadArgs args) {
    S.acquire();
    std::cout << "Start of thread (" << args.mytid << ", " << std::this_thread::get_id() << ") " << std::endl;
    S.release();
    for(int i = 0 ; i < args.addMax; i++) {
	S.acquire();
        ++counter;
	S.release();
    }

    std::cout << "End of thread (" << args.mytid << ", " << std::this_thread::get_id() << ")" << std::endl;

}

void first(int addMax, int N) {

    std::thread threads[N];

    for (int i = 0 ; i < N ; i++) {
        threadArgs args = {addMax, i + 1};
        threads[i] = std::thread(threadFunction, args);
    }

    for (int i = 0 ; i < N ; i++) {
        if (threads[i].joinable()) {
            threads[i].join();
        }
    }

    std::cout << "First: Actual counter value = " << counter;
    std::cout << "; Theoretical counter value = " << (N * addMax) << std::endl;

}

void second(int addMax, int N) {

    std::thread threads[N];

    for (int i = 0 ; i < N ; i++) {
        threadArgs args = {addMax, i + 1};
        threads[i] = std::thread(threadFunction2, args);
    }

    for (int i = 0 ; i < N ; i++) {
        if (threads[i].joinable()) {
            threads[i].join();
        }
    }

    std::cout << "Second: Actual counter value = " << counter;
    std::cout << "; Theoretical counter value = " << (N * addMax) << std::endl;

}

int main() {

    int N;
    int addMax;

    std::cout << "Enter desired number of threads 1-19: ";
    std::cin >> N;

    std::cout << "Enter int addMax: ";
    std::cin >> addMax;

    first(addMax, N);

    counter = 0;

    second(addMax, N);

    return 0;

}

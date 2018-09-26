// CMakeSample.cpp : Defines the entry point for the application.
//

#include "CMakeSample.h"
#include <iostream>
#include <asio.hpp>

#include <iostream>
#include <memory>
#include <thread>
#include <chrono>
#include <mutex>

using namespace std;


struct Base
{
    Base() { std::cout << "  Base::Base()\n"; }
    // Note: non-virtual destructor is OK here
    ~Base() { std::cout << "  Base::~Base()\n"; }
};

struct Derived : public Base
{
    Derived() { std::cout << "  Derived::Derived()\n"; }
    ~Derived() { std::cout << "  Derived::~Derived()\n"; }
};

void thr(std::shared_ptr<Base> p)
{
    std::this_thread::sleep_for(std::chrono::seconds(1));
    std::shared_ptr<Base> lp = p; // thread-safe, even though the
                                  // shared use_count is incremented
    {
        static std::mutex io_mutex;
        std::lock_guard<std::mutex> lk(io_mutex);
        std::cout << "local pointer in a thread:\n"
            << "  lp.get() = " << lp.get()
            << ", lp.use_count() = " << lp.use_count() << '\n';
    }
    cout << "-------------- end" << endl;
}

int main()
{
    // share_ptr example
    std::shared_ptr<Base> p = std::make_shared<Derived>();

    std::cout << "Created a shared Derived (as a pointer to Base)\n"
        << "  p.get() = " << p.get()
        << ", p.use_count() = " << p.use_count() << '\n';
    std::thread t1(thr, p), t2(thr, p), t3(thr, p);
    p.reset(); // release ownership from main
    std::cout << "Shared ownership between 3 threads and released\n"
        << "ownership from main:\n"
        << "  p.get() = " << p.get()
        << ", p.use_count() = " << p.use_count() << '\n';
    t1.join(); t2.join(); t3.join();
    std::cout << "All threads completed, the last one deleted Derived\n";

    // string find char example
    const string str = u8"nothing-filename";
    size_t pos = str.find_last_of('\\');
    if (pos == string::npos)
        cout << "not path" << endl;
    cout << "name : " << str << endl;
    cout << "pos : " << pos << endl;

    const string str2 = u8"C:\\xxxx.txt";
    size_t pos2 = str2.find_last_of('\\');
    cout << "name: " << str2 << endl;
    cout << "pos : " << pos2 << endl;

    string x = str2.substr(pos2 + 1);
    cout << "x : " << x << endl;

    // asio example
    asio::io_context io;
    asio::steady_timer t(io, asio::chrono::seconds(5));
    t.wait();
    cout << "Hello CMake." << endl;
    return 0;
}

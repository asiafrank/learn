// CMakeSample.cpp : Defines the entry point for the application.
//

#include "CMakeSample.h"
#include <fstream>
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

void testSome();

using Byte = std::uint8_t;

int main()
{
    {
        vector<Byte> buf(4);
        std::size_t fileLength = 454001;
        int len = (uint32_t)fileLength;
        for (int i = 0; i < 4; i++)
        {
            uint32_t tmp = (len >> ((3 - i) * 8)) & 0x000000FF;
            buf[i] = (Byte)tmp;
        }

        cout << std::hex;
        // print Bytes of buf
        for (size_t i = 0; i < buf.size(); i++)
        {
            cout << (uint32_t)buf[i] << " ";
        }
        cout << endl;

        // re construct to datalength
        auto it = buf.cbegin();
        std::uint32_t dataLength = 0;
        for (int i = 3; i >= 0; i--, it++)
        {
            uint32_t tmp = (uint32_t)(*it);
            tmp = (tmp << (i * 8));

            cout << tmp << " ";
            dataLength = (dataLength | tmp);
        }
        cout << endl << std::dec;
        cout << "dataLength: " << dataLength << endl;
    }

    {
        // progress;
        float progress = 0.0;
        while (progress < 1.0) {
            int barWidth = 70;

            std::cout << "[";
            int pos = barWidth * progress;
            for (int i = 0; i < barWidth; ++i) {
                if (i < pos) std::cout << "=";
                else if (i == pos) std::cout << ">";
                else std::cout << " ";
            }
            std::cout << "] " << int(progress * 100.0) << " %\r";
            std::cout.flush();

            progress += 0.16; // for demonstration only
        }
        std::cout << std::endl;
    }

    // testSome();
    return 0;
}

void testSome()
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

    {
        // vector example
        vector<Base> v; // 不能直接用数字构造 vector 因为它会构造相应数量的 Base 对象
        v.reserve(10);  // 改变 capacity 为 10，即预留 10 数量空间
        cout << "vector max_size: " << v.max_size() << endl;
        cout << "vector size: " << v.size() << endl;
        cout << "vector capacity: " << v.capacity() << endl;
        cout << "vector empty: " << v.empty() << endl;

        // array example
        array<Base, 10> arr; // 最好不使用默认构造函数，因为它会构造相应数量的 Base 对象，
                             // 用 aggregate initialization
                             // 明显 vector 更灵活
        cout << "array max_size: " << arr.max_size() << endl;
        cout << "array size: " << arr.size() << endl;
        cout << "array empty: " << arr.empty() << endl;
    }

    {
        // file test
        int success = std::remove("test.data");
        cout << "delete file success: " << success << endl;
    }

    {
        // read file test
        vector<char> v(1024); // 1KB
        string filePath = "E:\\personal\\learn\\cpp\\asio-learn\\asio-learn\\asio-learn.cpp";
        shared_ptr<ifstream> pfile = make_shared<ifstream>(filePath, std::ios::ate | ::ios::binary);
        if (pfile->is_open())
        {
            std::streampos pos = pfile->tellg();
            size_t remain = (size_t)pos;
            size_t blockSize = 200;
            size_t blockNumbers = remain / blockSize + 1;
            size_t readSize = 0;
            if (remain > blockSize)
                readSize = blockSize;
            else
                readSize = remain;

            for (size_t i = 0; i < blockNumbers; i++)
            {
                cout << "------- remain: " << remain << endl;
                cout << "------- block " << i << " ---------" << endl;

                if (remain > blockSize)
                    readSize = blockSize;
                else
                    readSize = remain;

                pfile->seekg(i * blockSize, std::ios::beg);
                pfile->read(&v[0], readSize);
                remain -= readSize;

                for (size_t i = 0; i < readSize; i++)
                {
                    cout << v[i];
                }
                cout << endl;
            }
        }
        else
        {
            cout << "fail to open" << endl;
        }
    }

    {
        // thread example
        std::thread th([]() {
            cout << "thread execute" << endl;
        });
        th.detach();
    }

    {
        uintmax_t x = 4 * 1024 * 1024;
        cout << "uintmax_t " << x << endl;
    }

    // asio example
    asio::io_context io;
    asio::steady_timer t(io, asio::chrono::seconds(5));
    t.wait();
    cout << "Hello CMake." << endl;
}

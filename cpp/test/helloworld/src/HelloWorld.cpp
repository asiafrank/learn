//
//  HelloWorld.cpp
//  cpp
//
//  Created by asiafrank on 30/1/2017.
//
//
#include <iostream>
#include <ctime>
#include <cstring>
#include <assert.h>

using namespace std;

struct Books {
    char title[50];
    char author[50];
    char subject[100];
    int book_id;
};

class Box {
public:
    double length;   // Length of a box
    double breadth;  // Breadth of a box
    double height;   // Height of a box
};

// Base class
class Shape  {
public:
    void setWidth(int w) {
        width = w;
    }

    void setHeight(int h) {
        height = h;
    }

protected:
    int width;
    int height;
};
// Derived class
class Rectangle: public Shape {
public:
    int getArea() {
        return (width * height);
    }
};

int main() {
    cout << "Hello World" << endl;
    cout << "Size of char: " << sizeof(char) << endl;
    cout << "Size of int: " << sizeof(int) << endl;
    cout << "Size of short int: " << sizeof(short int) << endl;
    cout << "Size of long int: " << sizeof(long int) << endl;
    cout << "Size of long: " << sizeof(long) << endl;
    cout << "Size of float: " << sizeof(float) << endl;
    cout << "Size of double: " << sizeof(double) << endl;
    cout << "Size of wchar_t: " << sizeof(wchar_t) << endl;

    //===========================================================
    // time
    //===========================================================
    // current date/time based on current system
    time_t now = time(0);

    // convert now to string form
    char *dt = ctime(&now);

    cout << "The local date and time is: " << dt;

    // convert now to tm struct for UTC
    tm *gmtm = gmtime(&now);
    dt = asctime(gmtm);
    cout << "The UTC date and time is:" << dt;

    tm *ltm = localtime(&now);

    // print various components of tm structure.
    cout << "Year: " << 1900 + ltm->tm_year << endl;
    cout << "Month: " << 1 + ltm->tm_mon << endl;
    cout << "Day: " << ltm->tm_mday << endl;
    cout << "Time: " << ltm->tm_hour << ":";
    cout << ltm->tm_min << ":";
    cout << ltm->tm_sec << endl;

    //===========================================================
    // struct
    //===========================================================
    struct Books book1;        // Declare Book1 of type Book
    struct Books book2;        // Declare Book2 of type Book

    // book 1 specification
    strcpy(book1.title, "Learn C++ Programming");
    strcpy(book1.author, "Chand Miyan");
    strcpy(book1.subject, "C++ Programming");
    book1.book_id = 6495407;

    // book 2 specification
    strcpy(book2.title, "Telecom Billing");
    strcpy(book2.author, "Yakit Singha");
    strcpy(book2.subject, "Telecom");
    book2.book_id = 6495700;

    // Print Book1 info
    cout << "Book 1 title : " << book1.title << endl;
    cout << "Book 1 author : " << book1.author << endl;
    cout << "Book 1 subject : " << book1.subject << endl;
    cout << "Book 1 id : " << book1.book_id << endl;

    // Print Book2 info
    cout << "Book 2 title : " << book2.title << endl;
    cout << "Book 2 author : " << book2.author << endl;
    cout << "Book 2 subject : " << book2.subject << endl;
    cout << "Book 2 id : " << book2.book_id << endl;

    //===========================================================
    // Class
    //===========================================================

    Box Box1;        // Declare Box1 of type Box
    Box Box2;        // Declare Box2 of type Box
    double volume = 0.0;     // Store the volume of a box here

    // box 1 specification
    Box1.height = 5.0;
    Box1.length = 6.0;
    Box1.breadth = 7.0;

    // box 2 specification
    Box2.height = 10.0;
    Box2.length = 12.0;
    Box2.breadth = 13.0;

    // volume of box 1
    volume = Box1.height * Box1.length * Box1.breadth;
    cout << "Volume of Box1 : " << volume <<endl;

    // volume of box 2
    volume = Box2.height * Box2.length * Box2.breadth;
    cout << "Volume of Box2 : " << volume <<endl;

    //===========================================================
    // Inheritance
    //===========================================================

    Rectangle Rect;

    Rect.setWidth(5);
    Rect.setHeight(7);

    // Print the area of the object.
    cout << "Total area: " << Rect.getArea() << endl;

    int x = 0;
    int &r = x;
    int *p = &x;
    int *p2 = &r;
    cout << "hh: " + (p == p2) << endl;
    return 0;
}

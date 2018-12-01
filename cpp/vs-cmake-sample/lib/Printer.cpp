#include "Printer.h"
#include <iostream>
using namespace std;
void Printer::print(){
    cout<<"Hello World"<<endl;
}

Printer::Printer() {
    cout<<"Printer Object Constructed"<<endl;
 }

 Printer::~Printer(){
     cout<<"Printer Object Destructed"<<endl;
 }
// mess-code.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <fstream>

class Config
{
public:
	Config();
	~Config();
};

Config::Config()
{
	std::cout << "config constructed" << std::endl;
}

Config::~Config()
{
	std::cout << "config descruted" << std::endl;
}

int main()
{
	Config *c = new Config();
	delete c;
    return 0;
}

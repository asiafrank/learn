#include "stdafx.h"
#include "G.h"
#include <iostream>

G::G()
{
	std::cout << "constructor invoked" << std::endl;
}

G::G(const G&) { std::cout << "A copy was made.\n"; }


G::~G()
{
    std::cout << "release invoked" << std::endl;
}

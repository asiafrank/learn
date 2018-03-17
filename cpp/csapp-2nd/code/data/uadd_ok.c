#include <stdio.h>
#include <limits.h>

int uadd_ok(unsigned x, unsigned y) 
{
    unsigned result = x + y;
    if (result < x) {
        return 0;
    } else {
        return 1;
    }
}

int main(int argc, char const *argv[])
{
    printf("is unsigned add ok: %d\n", uadd_ok(UINT_MAX, 10));
    return 0;
}
#include <stdio.h>
#include <limits.h>

int tadd_ok(int x, int y) 
{
    int result = x + y;
    if ((x > 0 && y > 0 && result < 0)
        || (x < 0 && y < 0 && result > 0)) {
        return 0;
    } else {
        return 1;
    }
}

int main(int argc, char const *argv[])
{
    printf("is 2's complement add ok: %d\n", tadd_ok(INT_MAX, 10));
    return 0;
}
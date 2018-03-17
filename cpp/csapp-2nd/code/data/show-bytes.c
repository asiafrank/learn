#include "show_byte_order.h"
#include <string.h>

void test_show_bytes(int val)
{
    int ival = val;
    float fval = (float) ival;
    int *pval = &ival;
    show_int(ival);
    show_float(fval);
    show_pointer(pval);

    const char *s = "abcdef";
    show_bytes((byte_pointer) s, strlen(s));
}

int main(int argc, char const *argv[])
{
    test_show_bytes(12345);
    return 0;
}
#ifndef DEVCAPS_H
#define DEVCAPS_H

#include <Windows.h>

#define NUMLINES ((int) (sizeof(devcaps) / sizeof(devcaps[0])))

struct DC
{
    int index;
    TCHAR *szLable;
    TCHAR *szDesc;
};

DC devcaps[] = {
    { HORZSIZE, L"HORZSIZE", L"Width in millimeters:" },
    { VERTSIZE, L"VERTRES", L"Height in millimeters:" },
    { HORZRES, L"HORZRES", L"Width in pixels:" },
    { VERTRES, L"VERTRES", L"Height in raster lines:" },
    { BITSPIXEL, L"BITSPIXEL", L"Color bits per pixel:" },
    { PLANES, L"PLANES", L"Number of color planes:" },
    { NUMBRUSHES, L"NUMBRUSHES", L"Number of device brushes:" },
    { NUMPENS, L"NUMPENS", L"Number of device pens:" },
    { NUMMARKERS, L"NUMMARKERS", L"Number of device markers:" },
    { NUMFONTS, L"NUMFONTS", L"Number of device fonts:" },
    { NUMCOLORS, L"NUMCOLORS", L"Number of device colors:" },
    { PDEVICESIZE, L"PDEVICESIZE", L"Size of device structure:" },
    { ASPECTX, L"ASPECTX", L"Relative width of pixel:" },
    { ASPECTY, L"ASPECTY", L"Relative height of pixel:" },
    { ASPECTXY, L"ASPECTXY", L"Relative diagonal of pixel:" },
    { LOGPIXELSX, L"LOGPIXELSX", L"horizontal dts per inch:" },
    { LOGPIXELSY, L"LOGPIXELSY", L"Vertical dots per inch:" },
    { SIZEPALETTE, L"SIZEPALETTE", L"Number of palette entries:" },
    { NUMRESERVED, L"NUMRESERVED", L"Reserved palette entries:" },
    { COLORRES, L"COLORRES", L"Actual color resolutioin:" }
};

#endif // !DEVCAPS_H

#pragma once
#include <Windows.h>

void DibFileInitialize(HWND hwnd);
BOOL DibFileOpenDlg(HWND hwnd, PTSTR pstrFileName, PTSTR pstrTitleName);
BOOL DibFileSaveDlg(HWND hwnd, PTSTR pstrFileName, PTSTR pstrTitleName);
BITMAPFILEHEADER * DibLoadImage(PCTSTR pstrFileName);
BOOL DibSaveImage(PTSTR pstrFileName, BITMAPFILEHEADER *);
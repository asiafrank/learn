
#include "stdafx.h"
#include <commdlg.h>
#include "dibfile.h"

static OPENFILENAME ofn;

void DibFileInitialize(HWND hwnd)
{
    static TCHAR szFilter[] = L"Bitmap Files (*.BMF)\0*.bmp\0All Files (*.*)\0*.*\0\0";
    ofn.lStructSize       = sizeof(OPENFILENAME);
    ofn.hwndOwner         = hwnd;
    ofn.hInstance         = NULL;
    ofn.lpstrFilter       = szFilter;
    ofn.lpstrCustomFilter = NULL;
    ofn.nMaxCustFilter    = 0;
    ofn.nFilterIndex      = 0;
    ofn.lpstrFile         = NULL;
    ofn.nMaxFile          = MAX_PATH;
    ofn.lpstrFileTitle    = NULL;
    ofn.nMaxFileTitle     = MAX_PATH;
    ofn.lpstrInitialDir   = NULL;
    ofn.lpstrTitle        = NULL;
    ofn.Flags             = 0;
    ofn.nFileOffset       = 0;
    ofn.nFileExtension    = 0;
    ofn.lpstrDefExt       = L"bmp";
    ofn.lCustData         = 0;
    ofn.lpfnHook          = NULL;
    ofn.lpTemplateName    = NULL;
}

BOOL DibFileOpenDlg(HWND hwnd, PTSTR pstrFileName, PTSTR pstrTitleName)
{
    ofn.hwndOwner      = hwnd;
    ofn.lpstrFile      = pstrFileName;
    ofn.lpstrFileTitle = pstrTitleName;
    ofn.Flags          = OFN_OVERWRITEPROMPT;

    return GetOpenFileName(&ofn);
}

BOOL DibFileSaveDlg(HWND hwnd, PTSTR pstrFileName, PTSTR pstrTitleName)
{
    ofn.hwndOwner      = hwnd;
    ofn.lpstrFile      = pstrFileName;
    ofn.lpstrFileTitle = pstrTitleName;
    ofn.Flags          = OFN_OVERWRITEPROMPT;

    return GetSaveFileName(&ofn);
}

BITMAPFILEHEADER * DibLoadImage(PCTSTR pstrFileName)
{
    BOOL             bSuccess;
    DWORD            dwFileSize, dwHeightSize, dwBytesRead;
    HANDLE           hFile;
    BITMAPFILEHEADER *pbmfh;

    hFile = CreateFile(pstrFileName, GENERIC_READ, FILE_SHARE_READ, NULL, 
                       OPEN_EXISTING, FILE_FLAG_SEQUENTIAL_SCAN, NULL);

    if (hFile == INVALID_HANDLE_VALUE)
        return NULL;

    dwFileSize = GetFileSize(hFile, &dwHeightSize);

    if (dwHeightSize)
    {
        CloseHandle(hFile);
        return NULL;
    }

    pbmfh = (BITMAPFILEHEADER*)malloc(dwFileSize);
    if (!pbmfh)
    {
        CloseHandle(hFile);
        return NULL;
    }

    bSuccess = ReadFile(hFile, pbmfh, dwFileSize, &dwBytesRead, NULL);
    CloseHandle(hFile);

    if (!bSuccess || (dwBytesRead != dwFileSize) 
        || (pbmfh->bfType != *(WORD*)"BM") 
        || (pbmfh->bfSize != dwFileSize))
    {
        free(pbmfh);
        return NULL;
    }
    return pbmfh;
}

BOOL DibSaveImage(PTSTR pstrFileName, BITMAPFILEHEADER * pbmfh)
{
    BOOL   bSuccess;
    DWORD  dwBytesWritten;
    HANDLE hFile;

    hFile = CreateFile(pstrFileName, GENERIC_WRITE, 0, NULL, 
                       CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);

    if (hFile == INVALID_HANDLE_VALUE)
        return FALSE;

    bSuccess = WriteFile(hFile, pbmfh, pbmfh->bfSize, &dwBytesWritten, NULL);
    CloseHandle(hFile);

    if (!bSuccess || (dwBytesWritten != pbmfh->bfSize))
    {
        DeleteFile(pstrFileName);
        return FALSE;
    }
    return TRUE;
}
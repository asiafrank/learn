#pragma once

#ifndef  PLAYER_H
#define  PLAYER_H

#include <new>
#include <Windows.h>
#include <ShObjIdl.h>
#include <assert.h>
#include <strsafe.h>

// Media Foundation headers
#include <mfapi.h>
#include <mfidl.h>
#include <Mferror.h>
#include <evr.h>

#include "resource.h"

template<class T>
void SafeRelease(T **ppT)
{
    if (*ppT)
    {
        (*ppT)->Release();
        *ppT = NULL;
    }
}

const UINT WM_APP_PLAYER_EVENT = WM_APP + 1;

enum PlayerState
{
    Closed = 0,  // No Session.
    Ready,       // Session was created, ready to open a file.
    OpenPending, // Session is opening a file.
    Started,     // Session is playing a file.
    Paused,      // Session is paused.
    Stopped,     // Session is stopped (ready to play).
    Closing,     // Application has closed the session, but is waiting for MESessionClosed.
};

class CPlayer : public IMFAsyncCallback
{
public:
    static HRESULT CreateInstance(HWND hVideo, HWND hEvent, CPlayer **ppPlayer);

    // IUnknown methods
    STDMETHODIMP QueryInterface(REFIID iid, void** ppv);
    STDMETHODIMP_(ULONG) AddRef();
    STDMETHODIMP_(ULONG) Release();

    // IMFAsyncCallback methods
    STDMETHODIMP GetParameters(DWORD*, DWORD*)
    {
        // Implementation of this method is optional.
        return E_NOTIMPL;
    }
    STDMETHODIMP Invoke(IMFAsyncResult* pAsyncResult);

    // Playback
    HRESULT  OpenURL(const WCHAR *sURL);
    HRESULT  Play();
    HRESULT  Pause();
    HRESULT  Stop();
    HRESULT  Shutdown();
    HRESULT  HandleEvent(UINT_PTR pUnkPtr);
    PlayerState GetState() const { return m_state; }

    // Video functionality
    HRESULT Repaint();
    HRESULT ResizeVideo(WORD width, WORD height);
    
    BOOL    HasVideo() const { return (m_pVideoDisplay != NULL); }

protected:
    // Constructor is private. Use static CreateInstance method to instantiate.
    CPlayer(HWND hVideo, HWND hEvent);

    // Destructor is private. Caller should call Release.
    virtual ~CPlayer();

    HRESULT Initialize();
    HRESULT CreateSession();
    HRESULT CloseSession();
    HRESULT StartPlayback();

    // Media event handlers
    virtual HRESULT OnTopologyStatus(IMFMediaEvent *pEvent);
    virtual HRESULT OnPresentationEnded(IMFMediaEvent *pEvent);
    virtual HRESULT OnNewPresentation(IMFMediaEvent *pEvent);
    
    // Override to handle additional session events.
    virtual HRESULT OnSessionEvent(IMFMediaEvent*, MediaEventType)
    {
        return S_OK;
    }
protected:
    long                   m_nRefCount;          // Reference count.
    
    IMFMediaSession        *m_pSession;
    IMFMediaSource         *m_pSource;
    IMFVideoDisplayControl *m_pVideoDisplay;

    HWND                   m_hwndVideo;        // Video window.
    HWND                   m_hwndEvent;        // App window to receive events.
    PlayerState            m_state;            // Current state of the media session.
    HANDLE                 m_hCloseEvent;      // Event to wait on while closing.
};

#endif // ! PLAYER_H

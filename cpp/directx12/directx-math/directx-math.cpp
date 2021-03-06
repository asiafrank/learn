// directx-math.cpp : Defines the entry point for the console application.
//

// Introduction To 3D Game Programming with DirectX12 

#include "stdafx.h"
#include <iostream>

using namespace std;
using namespace DirectX;
using namespace DirectX::PackedVector;

// Overload the "<<" operators so that we can use cout to 
// output XMVECTOR objects.
ostream& XM_CALLCONV operator<<(ostream& os, FXMVECTOR v)
{
    XMFLOAT3 dest;
    XMStoreFloat3(&dest, v);
    os << "(" << dest.x << ", " << dest.y << ", " << dest.z << ")";
    return os;
}

void setter_functions_sample();
void vector_functions();
void floating_point_error();

int main()
{
    cout.setf(ios_base::boolalpha);
    // Check support for SSE2 (Pentium4, AMD K8, and above).

    if (!XMVerifyCPUSupport())
    {
        cout << "directx math not supported" << endl;
        return 0;
    }
    
    setter_functions_sample();
    vector_functions();
    floating_point_error();
    return 0;
}


// 1.6.7 Setter Functions
void setter_functions_sample()
{
    XMVECTOR p = XMVectorZero();
    XMVECTOR q = XMVectorSplatOne();
    XMVECTOR u = XMVectorSet(1.0f, 2.0f, 3.0f, 0.0f);
    XMVECTOR v = XMVectorReplicate(-2.0f);
    XMVECTOR w = XMVectorSplatZ(u);

    cout << "p = " << p << endl;
    cout << "q = " << q << endl;
    cout << "u = " << u << endl;
    cout << "v = " << v << endl;
    cout << "w = " << w << endl;
    cout << "----------------------------------" << endl;
}

// 1.6.8 Vector Functions
void vector_functions()
{
    XMVECTOR n = XMVectorSet(1.0f, 0.0f, 0.0f, 0.0f);
    XMVECTOR u = XMVectorSet(1.0f, 2.0f, 3.0f, 0.0f);
    XMVECTOR v = XMVectorSet(-2.0f, 1.0f, -3.0f, 0.0f);
    XMVECTOR w = XMVectorSet(0.707f, 0.707f, 0.0f, 0.0f);

    // Vector addition: XMVECTOR operator +
    XMVECTOR a = u + v;

    // Vector subtraction: XMVECTOR operator -
    XMVECTOR b = u - v;

    // Scalar multiplication: XMVECTOR operator *
    XMVECTOR c = 10.0f*u;

    // ||u||
    XMVECTOR L = XMVector3Length(u);

    // d = u / ||u||
    XMVECTOR d = XMVector3Normalize(u);

    // s = u dot v
    XMVECTOR s = XMVector3Dot(u, v);

    // e = u x v
    XMVECTOR e = XMVector3Cross(u, v);

    // Find proj_n(w) and perp_n(w)
    XMVECTOR projW;
    XMVECTOR perpW;
    XMVector3ComponentsFromNormal(&projW, &perpW, w, n);

    // Does projW + perpW == w?
    bool equal = XMVector3Equal(projW + perpW, w) != 0;
    bool notEqual = XMVector3NotEqual(projW + perpW, w) != 0;

    // The angle between projW and perpW should be 90 degrees.
    XMVECTOR angleVec = XMVector3AngleBetweenVectors(projW, perpW);
    float angleRadians = XMVectorGetX(angleVec);
    float angleDegrees = XMConvertToDegrees(angleRadians);

    cout << "u              = " << u << endl;
    cout << "v              = " << v << endl;
    cout << "w              = " << w << endl;
    cout << "n              = " << w << endl;
    cout << "a = u + v      = " << a << endl;
    cout << "b = u - v      = " << b << endl;
    cout << "c = 10 * u     = " << c << endl;
    cout << "d = u / ||u||  = " << d << endl;
    cout << "e = u x v      = " << e << endl;
    cout << "L = ||u||      = " << L << endl;
    cout << "s = u.v        = " << s << endl;
    cout << "projW          = " << projW << endl;
    cout << "perpW          = " << perpW << endl;
    cout << "projW + perpW == w = " << equal << endl;
    cout << "projW + perpW != w = " << notEqual << endl;
    cout << "angle          = " << angleDegrees << endl;
    cout << "----------------------------------" << endl;
}

// 1.6.9 Floating-Point Error
void floating_point_error()
{
    cout.precision(8);
    XMVECTOR u = XMVectorSet(1.0f, 1.0f, 1.0f, 1.0f);
    XMVECTOR n = XMVector3Normalize(u);

    float LU = XMVectorGetX(XMVector3Length(n));

    // Mathematically, the length should be 1. Is it numerically?
    cout << LU << endl;
    if (LU == 1.0f)
        cout << "Length 1" << endl;
    else
        cout << "Length not 1" << endl;

    // Raising 2 to any power should still be 1. Is it?
    float powLU = powf(LU, 1.0e6f);
    cout << "LU^(10^6) = " << powLU << endl;
    cout << "----------------------------------" << endl;
}
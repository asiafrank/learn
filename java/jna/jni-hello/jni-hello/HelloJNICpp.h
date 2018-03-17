#pragma once
#include <jni.h>

#ifndef _Included_HelloJNICpp
#define _Included_HelloJNICpp
#ifdef __cplusplus
extern "C" {
#endif // __cplusplus

    /*
    Class:     com.asiafrank.jni.HelloJNICpp
    Method:    sayHello
    Signature: ()V
    */
    JNIEXPORT void JNICALL Java_com_asiafrank_jni_HelloJNICpp_sayHello(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif // _cplusplus
#endif // !_Included_HelloJNI
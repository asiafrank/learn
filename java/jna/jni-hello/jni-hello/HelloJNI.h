#pragma once
#include <jni.h>

#ifndef _Included_HelloJNI
#define _Included_HelloJNI
#ifdef __cplusplus
extern "C" {
#endif // __cplusplus

    /*
    Class:     com.asiafrank.jni.HelloJNI
    Method:    sayHello
    Signature: ()V
    */
    JNIEXPORT void JNICALL Java_com_asiafrank_jni_HelloJNI_sayHello(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif // _cplusplus
#endif // !_Included_HelloJNI

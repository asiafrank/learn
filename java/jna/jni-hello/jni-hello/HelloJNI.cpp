#include <jni.h>
#include <stdio.h>
#include "HelloJNI.h"

// Implementation of native method sayHello() of HelloJNI class

JNIEXPORT void JNICALL Java_com_asiafrank_jni_HelloJNI_sayHello(JNIEnv *, jobject)
{
    printf("Hello World!\n");
    return;
}
